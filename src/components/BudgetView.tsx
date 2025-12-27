import { useState, useEffect } from 'react';
import { MonthlyBudget, Record } from '../types';
import { storage } from '../utils/storage';
import { getNextMonth, formatDate } from '../utils/date';
import { calculateTotal } from '../utils/calculations';
import './BudgetView.css';

interface BudgetViewProps {
  month: string;
}

export default function BudgetView({ month }: BudgetViewProps) {
  const [budget, setBudget] = useState<MonthlyBudget | null>(null);
  const [expectedExpense, setExpectedExpense] = useState('0');
  const [expectedIncome, setExpectedIncome] = useState('0');
  const [budgetRecords, setBudgetRecords] = useState<Record[]>([]);
  const [showAddBudgetRecord, setShowAddBudgetRecord] = useState(false);

  useEffect(() => {
    loadBudget();
  }, [month]);

  const loadBudget = () => {
    const budgetData = storage.getOrCreateBudget(month);
    setBudget(budgetData);
    setExpectedExpense(budgetData.expectedExpense.toString());
    setExpectedIncome(budgetData.expectedIncome.toString());
    setBudgetRecords(budgetData.records || []);
  };

  const handleSaveBudget = () => {
    if (!budget) return;

    const updated: MonthlyBudget = {
      ...budget,
      expectedExpense: parseFloat(expectedExpense) || 0,
      expectedIncome: parseFloat(expectedIncome) || 0,
    };

    storage.updateBudget(updated);
    setBudget(updated);
    alert('预算已保存');
  };

  const handleAddBudgetRecord = (record: Record) => {
    if (!budget) return;

    const updatedRecords = [...budgetRecords, record];
    const updated: MonthlyBudget = {
      ...budget,
      records: updatedRecords,
    };

    storage.updateBudget(updated);
    setBudgetRecords(updatedRecords);
    setShowAddBudgetRecord(false);
  };

  const handleDeleteBudgetRecord = (id: string) => {
    if (!budget) return;

    const updatedRecords = budgetRecords.filter(r => r.id !== id);
    const updated: MonthlyBudget = {
      ...budget,
      records: updatedRecords,
    };

    storage.updateBudget(updated);
    setBudgetRecords(updatedRecords);
  };

  const actualExpense = calculateTotal(budgetRecords, 'expense');
  const actualIncome = calculateTotal(budgetRecords, 'income');
  const expenseProgress = budget?.expectedExpense ? (actualExpense / budget.expectedExpense) * 100 : 0;
  const incomeProgress = budget?.expectedIncome ? (actualIncome / budget.expectedIncome) * 100 : 0;

  const isNextMonth = month === getNextMonth();

  return (
    <div className="budget-view">
      {!isNextMonth && (
        <div className="budget-notice">
          <p>💡 提示：预算功能主要用于规划下个月的支出和收入</p>
          <p className="budget-hint">当前显示的是 {formatDate(month + '-01', 'yyyy年MM月')} 的预算</p>
        </div>
      )}

      <div className="budget-section">
        <h3 className="section-title">预计预算</h3>
        <div className="budget-input-group">
          <div className="budget-input-item">
            <label>预计支出</label>
            <input
              type="number"
              step="0.01"
              value={expectedExpense}
              onChange={(e) => setExpectedExpense(e.target.value)}
              placeholder="0.00"
            />
          </div>
          <div className="budget-input-item">
            <label>预计收入</label>
            <input
              type="number"
              step="0.01"
              value={expectedIncome}
              onChange={(e) => setExpectedIncome(e.target.value)}
              placeholder="0.00"
            />
          </div>
          <button className="btn-save-budget" onClick={handleSaveBudget}>
            保存预算
          </button>
        </div>
      </div>

      {((budget.expectedExpense ?? 0) > 0 || (budget.expectedIncome ?? 0) > 0) && (
        <div className="budget-progress-section">
          <h3 className="section-title">预算进度</h3>
          
          {(budget.expectedExpense ?? 0) > 0 && (
            <div className="progress-item">
              <div className="progress-header">
                <span className="progress-label">预计支出</span>
                <span className="progress-amount">
                  ¥{actualExpense.toFixed(2)} / ¥{(budget.expectedExpense ?? 0).toFixed(2)}
                </span>
              </div>
              <div className="progress-bar-container">
                <div
                  className={`progress-bar expense ${expenseProgress > 100 ? 'over' : ''}`}
                  style={{ width: `${Math.min(expenseProgress, 100)}%` }}
                />
              </div>
              <div className="progress-footer">
                <span className="progress-percent">
                  {expenseProgress.toFixed(1)}%
                </span>
                {expenseProgress > 100 && (
                  <span className="progress-warning">⚠️ 已超出预算</span>
                )}
              </div>
            </div>
          )}

          {(budget.expectedIncome ?? 0) > 0 && (
            <div className="progress-item">
              <div className="progress-header">
                <span className="progress-label">预计收入</span>
                <span className="progress-amount">
                  ¥{actualIncome.toFixed(2)} / ¥{(budget.expectedIncome ?? 0).toFixed(2)}
                </span>
              </div>
              <div className="progress-bar-container">
                <div
                  className="progress-bar income"
                  style={{ width: `${Math.min(incomeProgress, 100)}%` }}
                />
              </div>
              <div className="progress-footer">
                <span className="progress-percent">
                  {incomeProgress.toFixed(1)}%
                </span>
              </div>
            </div>
          )}
        </div>
      )}

      <div className="budget-records-section">
        <div className="section-header">
          <h3 className="section-title">预算记录</h3>
          <button
            className="btn-add-record"
            onClick={() => setShowAddBudgetRecord(true)}
          >
            + 添加记录
          </button>
        </div>

        {budgetRecords.length === 0 ? (
          <div className="empty-budget-records">
            <p>还没有预算记录</p>
            <p className="empty-hint">添加记录来跟踪预算使用情况</p>
          </div>
        ) : (
          <div className="budget-records-list">
            {budgetRecords.map(record => {
              const categories = storage.getCategories();
              const categoryInfo = categories.find(c => c.name === record.category) || {
                icon: '📝',
                color: '#6b7280',
              };

              return (
                <div key={record.id} className="budget-record-item">
                  <div className="budget-record-left">
                    <span
                      className="budget-record-icon"
                      style={{ backgroundColor: categoryInfo.color + '20', color: categoryInfo.color }}
                    >
                      {categoryInfo.icon}
                    </span>
                    <div>
                      <div className="budget-record-category">{record.category}</div>
                      <div className="budget-record-date">{formatDate(record.date, 'MM-dd')}</div>
                    </div>
                  </div>
                  <div className="budget-record-right">
                    <div className={`budget-record-amount ${record.type}`}>
                      {record.type === 'expense' ? '-' : '+'}¥{record.amount.toFixed(2)}
                    </div>
                    <button
                      className="budget-record-delete"
                      onClick={() => handleDeleteBudgetRecord(record.id)}
                    >
                      🗑️
                    </button>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </div>

      {showAddBudgetRecord && (
        <AddBudgetRecordModal
          month={month}
          onClose={() => setShowAddBudgetRecord(false)}
          onSave={handleAddBudgetRecord}
        />
      )}
    </div>
  );
}

interface AddBudgetRecordModalProps {
  month: string;
  onClose: () => void;
  onSave: (record: Record) => void;
}

function AddBudgetRecordModal({ onClose, onSave }: AddBudgetRecordModalProps) {
  const categories = storage.getCategories();
  const [type, setType] = useState<'expense' | 'income'>('expense');
  const [amount, setAmount] = useState('');
  const [category, setCategory] = useState('');
  const [date, setDate] = useState(formatDate(new Date(), 'yyyy-MM-dd\'T\'HH:mm'));
  const [note, setNote] = useState('');

  const filteredCategories = categories.filter(c => c.type === type);

  useEffect(() => {
    if (type && filteredCategories.length > 0 && !category) {
      setCategory(filteredCategories[0].name);
    }
  }, [type, filteredCategories, category]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!amount || parseFloat(amount) <= 0) {
      alert('请输入有效的金额');
      return;
    }

    if (!category) {
      alert('请选择分类');
      return;
    }

    const record: Record = {
      id: Date.now().toString() + Math.random().toString(36).substr(2, 9),
      type,
      amount: parseFloat(amount),
      category,
      date,
      note: note.trim() || undefined,
    };

    onSave(record);
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>添加预算记录</h2>
          <button className="close-btn" onClick={onClose}>×</button>
        </div>

        <form onSubmit={handleSubmit} className="modal-form">
          <div className="form-group type-selector">
            <button
              type="button"
              className={`type-btn expense ${type === 'expense' ? 'active' : ''}`}
              onClick={() => setType('expense')}
            >
              📉 支出
            </button>
            <button
              type="button"
              className={`type-btn income ${type === 'income' ? 'active' : ''}`}
              onClick={() => setType('income')}
            >
              📈 收入
            </button>
          </div>

          <div className="form-group">
            <label>金额</label>
            <input
              type="number"
              step="0.01"
              min="0.01"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              placeholder="0.00"
              required
            />
          </div>

          <div className="form-group">
            <label>分类</label>
            <div className="category-grid">
              {filteredCategories.map(cat => (
                <button
                  key={cat.id}
                  type="button"
                  className={`category-btn ${category === cat.name ? 'active' : ''}`}
                  onClick={() => setCategory(cat.name)}
                  style={{
                    backgroundColor: category === cat.name ? cat.color + '20' : 'transparent',
                    borderColor: cat.color,
                  }}
                >
                  <span className="category-icon">{cat.icon}</span>
                  <span className="category-name">{cat.name}</span>
                </button>
              ))}
            </div>
          </div>

          <div className="form-group">
            <label>日期时间</label>
            <input
              type="datetime-local"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label>备注（可选）</label>
            <textarea
              value={note}
              onChange={(e) => setNote(e.target.value)}
              placeholder="添加备注..."
              rows={3}
            />
          </div>

          <div className="modal-actions">
            <button type="button" className="btn-cancel" onClick={onClose}>
              取消
            </button>
            <button type="submit" className="btn-submit">
              添加
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

