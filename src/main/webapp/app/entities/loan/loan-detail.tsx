import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './loan.reducer';

export const LoanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const loanEntity = useAppSelector(state => state.loan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="loanDetailsHeading">
          <Translate contentKey="bankappApp.loan.detail.title">Loan</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{loanEntity.id}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="bankappApp.loan.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{loanEntity.amount}</dd>
          <dt>
            <Translate contentKey="bankappApp.loan.type">Type</Translate>
          </dt>
          <dd>{loanEntity.type ? loanEntity.type.name : ''}</dd>
          <dt>
            <Translate contentKey="bankappApp.loan.customer">Customer</Translate>
          </dt>
          <dd>{loanEntity.customer ? loanEntity.customer.username : ''}</dd>
          <dt>
            <Translate contentKey="bankappApp.loan.branch">Branch</Translate>
          </dt>
          <dd>{loanEntity.branch ? loanEntity.branch.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/loan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/loan/${loanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LoanDetail;
