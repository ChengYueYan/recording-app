import { format, startOfMonth, endOfMonth, eachDayOfInterval, isSameMonth, isSameDay, parseISO } from 'date-fns';
import { zhCN } from 'date-fns/locale';

export const formatDate = (date: string | Date, formatStr: string = 'yyyy-MM-dd'): string => {
  const d = typeof date === 'string' ? parseISO(date) : date;
  return format(d, formatStr);
};

export const getCurrentMonth = (): string => {
  return format(new Date(), 'yyyy-MM');
};

export const getNextMonth = (): string => {
  const date = new Date();
  date.setMonth(date.getMonth() + 1);
  return format(date, 'yyyy-MM');
};

export const getMonthDays = (year: number, month: number): Date[] => {
  const firstDay = startOfMonth(new Date(year, month - 1));
  const lastDay = endOfMonth(new Date(year, month - 1));
  return eachDayOfInterval({ start: firstDay, end: lastDay });
};

export const isCurrentMonth = (date: Date): boolean => {
  return isSameMonth(date, new Date());
};

export const isToday = (date: Date): boolean => {
  return isSameDay(date, new Date());
};

export const getMonthName = (date: Date): string => {
  return format(date, 'MMMM yyyy', { locale: zhCN });
};

