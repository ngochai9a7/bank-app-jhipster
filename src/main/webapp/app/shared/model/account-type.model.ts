export interface IAccountType {
  id?: number;
  name?: string | null;
  description?: string | null;
  interestRate?: number | null;
}

export const defaultValue: Readonly<IAccountType> = {};
