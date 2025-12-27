import { useState, useEffect } from 'react';
import { Record, RecordType } from '../types';
import { storage } from '../utils/storage';
import { formatDate } from '../utils/date';
import './AddRecordModal.css';

interface AddRecordModalProps {
  record?: Record | null;
  onClose: () => void;
  onSave: (record: Record) => void;
}

export default function AddRecordModal({ record, onClose, onSave }: AddRecordModalProps) {
  const categories = storage.getCategories();
  const [type, setType] = useState<RecordType>(record?.type || 'expense');
  const [amount, setAmount] = useState(record?.amount.toString() || '');
  const [category, setCategory] = useState(record?.category || '');
  const [date, setDate] = useState(record?.date || formatDate(new Date(), 'yyyy-MM-dd\'T\'HH:mm'));
  const [note, setNote] = useState(record?.note || '');

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

    const newRecord: Record = {
      id: record?.id || Date.now().toString() + Math.random().toString(36).substr(2, 9),
      type,
      amount: parseFloat(amount),
      category,
      date,
      note: note.trim() || undefined,
    };

    onSave(newRecord);
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>{record ? '编辑记录' : '添加记录'}</h2>
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
              {record ? '保存' : '添加'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

