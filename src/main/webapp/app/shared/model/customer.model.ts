import dayjs from 'dayjs';

export interface ICustomer {
  id?: number;
  username?: string | null;
  passhash?: string | null;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  registrationDate?: string | null;
}

export const defaultValue: Readonly<ICustomer> = {};
