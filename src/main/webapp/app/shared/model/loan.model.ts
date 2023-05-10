import { ILoanType } from 'app/shared/model/loan-type.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IBranch } from 'app/shared/model/branch.model';

export interface ILoan {
  id?: number;
  amount?: number | null;
  type?: ILoanType | null;
  customer?: ICustomer | null;
  branch?: IBranch | null;
}

export const defaultValue: Readonly<ILoan> = {};
