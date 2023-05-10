import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './transaction.reducer';

export const TransactionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const transactionEntity = useAppSelector(state => state.transaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transactionDetailsHeading">
          <Translate contentKey="bankappApp.transaction.detail.title">Transaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.id}</dd>
          <dt>
            <span id="datetime">
              <Translate contentKey="bankappApp.transaction.datetime">Datetime</Translate>
            </span>
          </dt>
          <dd>
            {transactionEntity.datetime ? (
              <TextFormat value={transactionEntity.datetime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="amount">
              <Translate contentKey="bankappApp.transaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.amount}</dd>
          <dt>
            <Translate contentKey="bankappApp.transaction.type">Type</Translate>
          </dt>
          <dd>{transactionEntity.type ? transactionEntity.type.name : ''}</dd>
          <dt>
            <Translate contentKey="bankappApp.transaction.from">From</Translate>
          </dt>
          <dd>{transactionEntity.from ? transactionEntity.from.id : ''}</dd>
          <dt>
            <Translate contentKey="bankappApp.transaction.to">To</Translate>
          </dt>
          <dd>{transactionEntity.to ? transactionEntity.to.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transaction/${transactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransactionDetail;
