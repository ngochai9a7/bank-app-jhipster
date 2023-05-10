import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { IBranch } from 'app/shared/model/branch.model';
import { getEntities as getBranches } from 'app/entities/branch/branch.reducer';
import { IAccountType } from 'app/shared/model/account-type.model';
import { getEntities as getAccountTypes } from 'app/entities/account-type/account-type.reducer';
import { IAccounts } from 'app/shared/model/accounts.model';
import { getEntity, updateEntity, createEntity, reset } from './accounts.reducer';

export const AccountsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const branches = useAppSelector(state => state.branch.entities);
  const accountTypes = useAppSelector(state => state.accountType.entities);
  const accountsEntity = useAppSelector(state => state.accounts.entity);
  const loading = useAppSelector(state => state.accounts.loading);
  const updating = useAppSelector(state => state.accounts.updating);
  const updateSuccess = useAppSelector(state => state.accounts.updateSuccess);

  const handleClose = () => {
    navigate('/accounts' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomers({}));
    dispatch(getBranches({}));
    dispatch(getAccountTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...accountsEntity,
      ...values,
      customer: customers.find(it => it.id.toString() === values.customer.toString()),
      branch: branches.find(it => it.id.toString() === values.branch.toString()),
      type: accountTypes.find(it => it.id.toString() === values.type.toString()),
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
          ...accountsEntity,
          customer: accountsEntity?.customer?.id,
          branch: accountsEntity?.branch?.id,
          type: accountsEntity?.type?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bankappApp.accounts.home.createOrEditLabel" data-cy="AccountsCreateUpdateHeading">
            <Translate contentKey="bankappApp.accounts.home.createOrEditLabel">Create or edit a Accounts</Translate>
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
                  id="accounts-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('bankappApp.accounts.openingDate')}
                id="accounts-openingDate"
                name="openingDate"
                data-cy="openingDate"
                type="date"
              />
              <ValidatedField
                label={translate('bankappApp.accounts.balance')}
                id="accounts-balance"
                name="balance"
                data-cy="balance"
                type="text"
              />
              <ValidatedField
                id="accounts-customer"
                name="customer"
                data-cy="customer"
                label={translate('bankappApp.accounts.customer')}
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
              <ValidatedField
                id="accounts-branch"
                name="branch"
                data-cy="branch"
                label={translate('bankappApp.accounts.branch')}
                type="select"
              >
                <option value="" key="0" />
                {branches
                  ? branches.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="accounts-type" name="type" data-cy="type" label={translate('bankappApp.accounts.type')} type="select">
                <option value="" key="0" />
                {accountTypes
                  ? accountTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/accounts" replace color="info">
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

export default AccountsUpdate;
