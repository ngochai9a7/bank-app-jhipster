export interface IBranch {
  id?: number;
  name?: string | null;
  address?: string | null;
  phone?: string | null;
}

export const defaultValue: Readonly<IBranch> = {};
