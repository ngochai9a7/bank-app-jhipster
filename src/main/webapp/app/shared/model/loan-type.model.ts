export interface ILoanType {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<ILoanType> = {};
