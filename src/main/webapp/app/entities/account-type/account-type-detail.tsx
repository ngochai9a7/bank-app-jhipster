import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './account-type.reducer';

export const AccountTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const accountTypeEntity = useAppSelector(state => state.accountType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accountTypeDetailsHeading">
          <Translate contentKey="bankappApp.accountType.detail.title">AccountType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accountTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="bankappApp.accountType.name">Name</Translate>
            </span>
          </dt>
          <dd>{accountTypeEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="bankappApp.accountType.description">Description</Translate>
            </span>
          </dt>
          <dd>{accountTypeEntity.description}</dd>
          <dt>
            <span id="interestRate">
              <Translate contentKey="bankappApp.accountType.interestRate">Interest Rate</Translate>
            </span>
          </dt>
          <dd>{accountTypeEntity.interestRate}</dd>
        </dl>
        <Button tag={Link} to="/account-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/account-type/${accountTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccountTypeDetail;
