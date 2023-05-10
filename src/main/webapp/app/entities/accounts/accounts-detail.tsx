import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './accounts.reducer';

export const AccountsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const accountsEntity = useAppSelector(state => state.accounts.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accountsDetailsHeading">
          <Translate contentKey="bankappApp.accounts.detail.title">Accounts</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.id}</dd>
          <dt>
            <span id="openingDate">
              <Translate contentKey="bankappApp.accounts.openingDate">Opening Date</Translate>
            </span>
          </dt>
          <dd>
            {accountsEntity.openingDate ? (
              <TextFormat value={accountsEntity.openingDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="balance">
              <Translate contentKey="bankappApp.accounts.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.balance}</dd>
          <dt>
            <Translate contentKey="bankappApp.accounts.customer">Customer</Translate>
          </dt>
          <dd>{accountsEntity.customer ? accountsEntity.customer.username : ''}</dd>
          <dt>
            <Translate contentKey="bankappApp.accounts.branch">Branch</Translate>
          </dt>
          <dd>{accountsEntity.branch ? accountsEntity.branch.name : ''}</dd>
          <dt>
            <Translate contentKey="bankappApp.accounts.type">Type</Translate>
          </dt>
          <dd>{accountsEntity.type ? accountsEntity.type.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/accounts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/accounts/${accountsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccountsDetail;
