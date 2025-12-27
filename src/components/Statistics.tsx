import { Record } from '../types';
import { groupByCategory, calculateTotal } from '../utils/calculations';
import { storage } from '../utils/storage';
import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer } from 'recharts';
import './Statistics.css';

interface StatisticsProps {
  records: Record[];
  month: string;
}

const COLORS = ['#ef4444', '#3b82f6', '#8b5cf6', '#ec4899', '#10b981', '#f59e0b', '#6366f1', '#6b7280'];

export default function Statistics({ records, month }: StatisticsProps) {
  const categories = storage.getCategories();
  
  const filteredRecords = records.filter(r => {
    const recordMonth = r.date.substring(0, 7);
    return recordMonth === month;
  });

  const expenseByCategory = groupByCategory(filteredRecords, 'expense');
  const incomeByCategory = groupByCategory(filteredRecords, 'income');

  const expenseData = Object.entries(expenseByCategory)
    .map(([name, value]) => {
      const cat = categories.find(c => c.name === name);
      return {
        name,
        value: Number(value.toFixed(2)),
        icon: cat?.icon || '📝',
        color: cat?.color || '#6b7280',
      };
    })
    .sort((a, b) => b.value - a.value);

  const incomeData = Object.entries(incomeByCategory)
    .map(([name, value]) => {
      const cat = categories.find(c => c.name === name);
      return {
        name,
        value: Number(value.toFixed(2)),
        icon: cat?.icon || '📝',
        color: cat?.color || '#6b7280',
      };
    })
    .sort((a, b) => b.value - a.value);

  const totalExpense = calculateTotal(filteredRecords, 'expense');
  const totalIncome = calculateTotal(filteredRecords, 'income');

  return (
    <div className="statistics">
      <div className="statistics-summary">
        <div className="stat-card">
          <div className="stat-label">总支出</div>
          <div className="stat-value expense">¥{totalExpense.toFixed(2)}</div>
        </div>
        <div className="stat-card">
          <div className="stat-label">总收入</div>
          <div className="stat-value income">¥{totalIncome.toFixed(2)}</div>
        </div>
      </div>

      {expenseData.length > 0 && (
        <div className="chart-section">
          <h3 className="chart-title">📉 支出分类统计</h3>
          <div className="chart-container">
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={expenseData}
                  cx="50%"
                  cy="50%"
                  labelLine={false}
                  label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                  outerRadius={100}
                  fill="#8884d8"
                  dataKey="value"
                >
                  {expenseData.map((_, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip formatter={(value: number) => `¥${value.toFixed(2)}`} />
              </PieChart>
            </ResponsiveContainer>
          </div>
          <div className="category-list">
            {expenseData.map((item, index) => (
              <div key={item.name} className="category-stat-item">
                <div className="category-stat-left">
                  <span className="category-stat-icon" style={{ color: COLORS[index % COLORS.length] }}>
                    {item.icon}
                  </span>
                  <span className="category-stat-name">{item.name}</span>
                </div>
                <div className="category-stat-right">
                  <span className="category-stat-amount">¥{item.value.toFixed(2)}</span>
                  <span className="category-stat-percent">
                    {totalExpense > 0 ? ((item.value / totalExpense) * 100).toFixed(1) : 0}%
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {incomeData.length > 0 && (
        <div className="chart-section">
          <h3 className="chart-title">📈 收入分类统计</h3>
          <div className="chart-container">
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={incomeData}
                  cx="50%"
                  cy="50%"
                  labelLine={false}
                  label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                  outerRadius={100}
                  fill="#8884d8"
                  dataKey="value"
                >
                  {incomeData.map((_, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip formatter={(value: number) => `¥${value.toFixed(2)}`} />
              </PieChart>
            </ResponsiveContainer>
          </div>
          <div className="category-list">
            {incomeData.map((item, index) => (
              <div key={item.name} className="category-stat-item">
                <div className="category-stat-left">
                  <span className="category-stat-icon" style={{ color: COLORS[index % COLORS.length] }}>
                    {item.icon}
                  </span>
                  <span className="category-stat-name">{item.name}</span>
                </div>
                <div className="category-stat-right">
                  <span className="category-stat-amount">¥{item.value.toFixed(2)}</span>
                  <span className="category-stat-percent">
                    {totalIncome > 0 ? ((item.value / totalIncome) * 100).toFixed(1) : 0}%
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {expenseData.length === 0 && incomeData.length === 0 && (
        <div className="empty-statistics">
          <div className="empty-icon">📊</div>
          <p>本月还没有数据</p>
          <p className="empty-hint">添加一些记录后查看统计</p>
        </div>
      )}
    </div>
  );
}

