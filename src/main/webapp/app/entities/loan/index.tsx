import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Loan from './loan';
import LoanDetail from './loan-detail';
import LoanUpdate from './loan-update';
import LoanDeleteDialog from './loan-delete-dialog';

const LoanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Loan />} />
    <Route path="new" element={<LoanUpdate />} />
    <Route path=":id">
      <Route index element={<LoanDetail />} />
      <Route path="edit" element={<LoanUpdate />} />
      <Route path="delete" element={<LoanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LoanRoutes;
