import { Record } from '../types';
import { format, parseISO } from 'date-fns';

export const calculateTotal = (records: Record[], type: 'expense' | 'income', month?: string): number => {
  let filtered = records.filter(r => r.type === type);
  
  if (month) {
    filtered = filtered.filter(r => {
      const recordDate = format(parseISO(r.date), 'yyyy-MM');
      return recordDate === month;
    });
  }
  
  return filtered.reduce((sum, record) => sum + record.amount, 0);
};

export const calculateBalance = (records: Record[], month?: string): number => {
  const income = calculateTotal(records, 'income', month);
  const expense = calculateTotal(records, 'expense', month);
  return income - expense;
};

export const groupByCategory = (records: Record[], type: 'expense' | 'income'): { [key: string]: number } => {
  const filtered = records.filter(r => r.type === type);
  const grouped: { [key: string]: number } = {};
  
  filtered.forEach(record => {
    grouped[record.category] = (grouped[record.category] || 0) + record.amount;
  });
  
  return grouped;
};

export const groupByDate = (records: Record[]): { [key: string]: Record[] } => {
  const grouped: { [key: string]: Record[] } = {};
  
  records.forEach(record => {
    const dateKey = format(parseISO(record.date), 'yyyy-MM-dd');
    if (!grouped[dateKey]) {
      grouped[dateKey] = [];
    }
    grouped[dateKey].push(record);
  });
  
  return grouped;
};

