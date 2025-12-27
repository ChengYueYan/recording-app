import { Record } from '../types';
import { formatDate } from '../utils/date';
import { storage } from '../utils/storage';
import './RecordList.css';

interface RecordListProps {
  records: Record[];
  month: string;
  onDelete: (id: string) => void;
  onEdit: (record: Record) => void;
}

export default function RecordList({ records, month, onDelete, onEdit }: RecordListProps) {
  const categories = storage.getCategories();
  
  const filteredRecords = records
    .filter(r => {
      const recordMonth = formatDate(r.date, 'yyyy-MM');
      return recordMonth === month;
    })
    .sort((a, b) => {
      return new Date(b.date).getTime() - new Date(a.date).getTime();
    });

  const getCategoryInfo = (categoryName: string) => {
    return categories.find(c => c.name === categoryName) || {
      icon: '📝',
      color: '#6b7280',
    };
  };

  if (filteredRecords.length === 0) {
    return (
      <div className="empty-state">
        <div className="empty-icon">📝</div>
        <p>本月还没有记录</p>
        <p className="empty-hint">点击右下角 + 按钮添加记录</p>
      </div>
    );
  }

  return (
    <div className="record-list">
      {filteredRecords.map(record => {
        const categoryInfo = getCategoryInfo(record.category);
        return (
          <div key={record.id} className={`record-item ${record.type}`}>
            <div className="record-left">
              <div
                className="record-icon"
                style={{ backgroundColor: categoryInfo.color + '20', color: categoryInfo.color }}
              >
                {categoryInfo.icon}
              </div>
              <div className="record-info">
                <div className="record-category">{record.category}</div>
                <div className="record-meta">
                  <span className="record-date">{formatDate(record.date, 'MM-dd HH:mm')}</span>
                  {record.note && <span className="record-note">· {record.note}</span>}
                </div>
              </div>
            </div>
            <div className="record-right">
              <div className={`record-amount ${record.type}`}>
                {record.type === 'expense' ? '-' : '+'}¥{record.amount.toFixed(2)}
              </div>
              <div className="record-actions">
                <button className="action-btn edit" onClick={() => onEdit(record)}>✏️</button>
                <button className="action-btn delete" onClick={() => onDelete(record.id)}>🗑️</button>
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
}

