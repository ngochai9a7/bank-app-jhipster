import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TransactionType from './transaction-type';
import TransactionTypeDetail from './transaction-type-detail';
import TransactionTypeUpdate from './transaction-type-update';
import TransactionTypeDeleteDialog from './transaction-type-delete-dialog';

const TransactionTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TransactionType />} />
    <Route path="new" element={<TransactionTypeUpdate />} />
    <Route path=":id">
      <Route index element={<TransactionTypeDetail />} />
      <Route path="edit" element={<TransactionTypeUpdate />} />
      <Route path="delete" element={<TransactionTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TransactionTypeRoutes;
