import { Record } from '../types';
import { calculateTotal, calculateBalance } from '../utils/calculations';
import './SummaryCard.css';

interface SummaryCardProps {
  records: Record[];
  month: string;
}

export default function SummaryCard({ records, month }: SummaryCardProps) {
  const income = calculateTotal(records, 'income', month);
  const expense = calculateTotal(records, 'expense', month);
  const balance = calculateBalance(records, month);

  return (
    <div className="summary-card">
      <div className="summary-item income">
        <div className="summary-label">
          <span className="summary-icon">📈</span>
          <span>本月收入</span>
        </div>
        <div className="summary-value income-value">¥{income.toFixed(2)}</div>
      </div>
      
      <div className="summary-item expense">
        <div className="summary-label">
          <span className="summary-icon">📉</span>
          <span>本月支出</span>
        </div>
        <div className="summary-value expense-value">¥{expense.toFixed(2)}</div>
      </div>
      
      <div className="summary-item balance">
        <div className="summary-label">
          <span className="summary-icon">💰</span>
          <span>本月结余</span>
        </div>
        <div className={`summary-value balance-value ${balance >= 0 ? 'positive' : 'negative'}`}>
          ¥{balance.toFixed(2)}
        </div>
      </div>
    </div>
  );
}

