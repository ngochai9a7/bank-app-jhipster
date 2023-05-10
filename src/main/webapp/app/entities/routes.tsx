import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Loan from './loan';
import LoanType from './loan-type';
import Customer from './customer';
import Branch from './branch';
import Accounts from './accounts';
import AccountType from './account-type';
import Transaction from './transaction';
import TransactionType from './transaction-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="loan/*" element={<Loan />} />
        <Route path="loan-type/*" element={<LoanType />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="branch/*" element={<Branch />} />
        <Route path="accounts/*" element={<Accounts />} />
        <Route path="account-type/*" element={<AccountType />} />
        <Route path="transaction/*" element={<Transaction />} />
        <Route path="transaction-type/*" element={<TransactionType />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
