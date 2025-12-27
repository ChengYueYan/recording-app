import { Record } from '../types';
import { formatDate } from './date';

export const exportToCSV = (records: Record[]): void => {
  if (records.length === 0) {
    alert('没有数据可导出');
    return;
  }

  const headers = ['日期', '类型', '金额', '分类', '备注'];
  const rows = records.map(record => [
    formatDate(record.date, 'yyyy-MM-dd HH:mm'),
    record.type === 'expense' ? '支出' : '收入',
    record.amount.toFixed(2),
    record.category,
    record.note || '',
  ]);

  const csvContent = [
    headers.join(','),
    ...rows.map(row => row.map(cell => `"${cell}"`).join(',')),
  ].join('\n');

  const BOM = '\uFEFF';
  const blob = new Blob([BOM + csvContent], { type: 'text/csv;charset=utf-8;' });
  const link = document.createElement('a');
  const url = URL.createObjectURL(blob);
  
  link.setAttribute('href', url);
  link.setAttribute('download', `记账记录_${formatDate(new Date(), 'yyyy-MM-dd')}.csv`);
  link.style.visibility = 'hidden';
  
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

export const exportToJSON = (records: Record[]): void => {
  if (records.length === 0) {
    alert('没有数据可导出');
    return;
  }

  const dataStr = JSON.stringify(records, null, 2);
  const blob = new Blob([dataStr], { type: 'application/json' });
  const link = document.createElement('a');
  const url = URL.createObjectURL(blob);
  
  link.setAttribute('href', url);
  link.setAttribute('download', `记账记录_${formatDate(new Date(), 'yyyy-MM-dd')}.json`);
  link.style.visibility = 'hidden';
  
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

