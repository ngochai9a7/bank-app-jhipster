import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';
import { IBranch } from 'app/shared/model/branch.model';
import { IAccountType } from 'app/shared/model/account-type.model';
import { ITransaction } from 'app/shared/model/transaction.model';

export interface IAccounts {
  id?: number;
  openingDate?: string | null;
  balance?: number | null;
  customer?: ICustomer | null;
  branch?: IBranch | null;
  type?: IAccountType | null;
  senders?: ITransaction[] | null;
  receivers?: ITransaction[] | null;
}

export const defaultValue: Readonly<IAccounts> = {};
