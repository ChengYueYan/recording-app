export type RecordType = 'expense' | 'income';

export interface Record {
  id: string;
  type: RecordType;
  amount: number;
  category: string;
  date: string;
  note?: string;
}

export interface MonthlyBudget {
  month: string; // YYYY-MM format
  expectedExpense: number;
  expectedIncome: number;
  records: Record[];
}

export interface Category {
  id: string;
  name: string;
  icon: string;
  color: string;
  type: RecordType;
}

