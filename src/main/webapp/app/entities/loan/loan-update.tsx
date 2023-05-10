import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILoanType } from 'app/shared/model/loan-type.model';
import { getEntities as getLoanTypes } from 'app/entities/loan-type/loan-type.reducer';
import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { IBranch } from 'app/shared/model/branch.model';
import { getEntities as getBranches } from 'app/entities/branch/branch.reducer';
import { ILoan } from 'app/shared/model/loan.model';
import { getEntity, updateEntity, createEntity, reset } from './loan.reducer';

export const LoanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const loanTypes = useAppSelector(state => state.loanType.entities);
  const customers = useAppSelector(state => state.customer.entities);
  const branches = useAppSelector(state => state.branch.entities);
  const loanEntity = useAppSelector(state => state.loan.entity);
  const loading = useAppSelector(state => state.loan.loading);
  const updating = useAppSelector(state => state.loan.updating);
  const updateSuccess = useAppSelector(state => state.loan.updateSuccess);

  const handleClose = () => {
    navigate('/loan' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLoanTypes({}));
    dispatch(getCustomers({}));
    dispatch(getBranches({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...loanEntity,
      ...values,
      type: loanTypes.find(it => it.id.toString() === values.type.toString()),
      customer: customers.find(it => it.id.toString() === values.customer.toString()),
      branch: branches.find(it => it.id.toString() === values.branch.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...loanEntity,
          type: loanEntity?.type?.id,
          customer: loanEntity?.customer?.id,
          branch: loanEntity?.branch?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bankappApp.loan.home.createOrEditLabel" data-cy="LoanCreateUpdateHeading">
            <Translate contentKey="bankappApp.loan.home.createOrEditLabel">Create or edit a Loan</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="loan-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('bankappApp.loan.amount')} id="loan-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField id="loan-type" name="type" data-cy="type" label={translate('bankappApp.loan.type')} type="select">
                <option value="" key="0" />
                {loanTypes
                  ? loanTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="loan-customer"
                name="customer"
                data-cy="customer"
                label={translate('bankappApp.loan.customer')}
                type="select"
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.username}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="loan-branch" name="branch" data-cy="branch" label={translate('bankappApp.loan.branch')} type="select">
                <option value="" key="0" />
                {branches
                  ? branches.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/loan" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default LoanUpdate;
