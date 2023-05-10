import dayjs from 'dayjs';
import { ITransactionType } from 'app/shared/model/transaction-type.model';
import { IAccounts } from 'app/shared/model/accounts.model';

export interface ITransaction {
  id?: number;
  datetime?: string | null;
  amount?: number | null;
  type?: ITransactionType | null;
  from?: IAccounts | null;
  to?: IAccounts | null;
}

export const defaultValue: Readonly<ITransaction> = {};
