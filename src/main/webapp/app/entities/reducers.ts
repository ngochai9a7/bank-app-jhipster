import loan from 'app/entities/loan/loan.reducer';
import loanType from 'app/entities/loan-type/loan-type.reducer';
import customer from 'app/entities/customer/customer.reducer';
import branch from 'app/entities/branch/branch.reducer';
import accounts from 'app/entities/accounts/accounts.reducer';
import accountType from 'app/entities/account-type/account-type.reducer';
import transaction from 'app/entities/transaction/transaction.reducer';
import transactionType from 'app/entities/transaction-type/transaction-type.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  loan,
  loanType,
  customer,
  branch,
  accounts,
  accountType,
  transaction,
  transactionType,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
