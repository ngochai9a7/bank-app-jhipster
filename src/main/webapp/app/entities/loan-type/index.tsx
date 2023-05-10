import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LoanType from './loan-type';
import LoanTypeDetail from './loan-type-detail';
import LoanTypeUpdate from './loan-type-update';
import LoanTypeDeleteDialog from './loan-type-delete-dialog';

const LoanTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LoanType />} />
    <Route path="new" element={<LoanTypeUpdate />} />
    <Route path=":id">
      <Route index element={<LoanTypeDetail />} />
      <Route path="edit" element={<LoanTypeUpdate />} />
      <Route path="delete" element={<LoanTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LoanTypeRoutes;
