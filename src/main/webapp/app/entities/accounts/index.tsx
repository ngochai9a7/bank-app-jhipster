import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Accounts from './accounts';
import AccountsDetail from './accounts-detail';
import AccountsUpdate from './accounts-update';
import AccountsDeleteDialog from './accounts-delete-dialog';

const AccountsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Accounts />} />
    <Route path="new" element={<AccountsUpdate />} />
    <Route path=":id">
      <Route index element={<AccountsDetail />} />
      <Route path="edit" element={<AccountsUpdate />} />
      <Route path="delete" element={<AccountsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AccountsRoutes;
