import { useState } from 'react';
import { Record } from '../types';
import { format, parseISO, startOfMonth, endOfMonth, eachDayOfInterval, isSameMonth, isToday, isSameDay, startOfWeek, endOfWeek } from 'date-fns';
import { zhCN } from 'date-fns/locale';
import { calculateTotal } from '../utils/calculations';
import { storage } from '../utils/storage';
import './CalendarView.css';

interface CalendarViewProps {
  records: Record[];
  month: string;
  onMonthChange: (month: string) => void;
}

export default function CalendarView({ records, month }: CalendarViewProps) {
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const currentDate = parseISO(month + '-01');

  const monthStart = startOfMonth(currentDate);
  const monthEnd = endOfMonth(currentDate);
  const calendarStart = startOfWeek(monthStart, { weekStartsOn: 1 });
  const calendarEnd = endOfWeek(monthEnd, { weekStartsOn: 1 });
  const days = eachDayOfInterval({ start: calendarStart, end: calendarEnd });

  const getDayRecords = (date: Date): Record[] => {
    const dateStr = format(date, 'yyyy-MM-dd');
    return records.filter(r => {
      const recordDate = format(parseISO(r.date), 'yyyy-MM-dd');
      return recordDate === dateStr;
    });
  };

  const getDayTotal = (date: Date, type: 'expense' | 'income'): number => {
    const dayRecords = getDayRecords(date);
    return calculateTotal(dayRecords, type);
  };

  const selectedRecords = selectedDate ? getDayRecords(selectedDate) : [];

  return (
    <div className="calendar-view">
      <div className="calendar-container">
        <div className="calendar-header">
          <div className="weekdays">
            {['一', '二', '三', '四', '五', '六', '日'].map(day => (
              <div key={day} className="weekday">{day}</div>
            ))}
          </div>
        </div>
        
        <div className="calendar-grid">
          {days.map(day => {
            const isCurrentMonthDay = isSameMonth(day, currentDate);
            const isSelected = selectedDate && isSameDay(day, selectedDate);
            const expense = getDayTotal(day, 'expense');
            const income = getDayTotal(day, 'income');
            const hasRecords = expense > 0 || income > 0;

            return (
              <div
                key={day.toString()}
                className={`calendar-day ${!isCurrentMonthDay ? 'other-month' : ''} ${isToday(day) ? 'today' : ''} ${isSelected ? 'selected' : ''} ${hasRecords ? 'has-records' : ''}`}
                onClick={() => isCurrentMonthDay && setSelectedDate(day)}
              >
                <div className="day-number">{format(day, 'd')}</div>
                {hasRecords && (
                  <div className="day-summary">
                    {expense > 0 && <span className="day-expense">-¥{expense.toFixed(0)}</span>}
                    {income > 0 && <span className="day-income">+¥{income.toFixed(0)}</span>}
                  </div>
                )}
              </div>
            );
          })}
        </div>
      </div>

      {selectedDate && selectedRecords.length > 0 && (
        <div className="day-details">
          <h3 className="day-details-title">
            {format(selectedDate, 'yyyy年MM月dd日', { locale: zhCN })}
          </h3>
          <div className="day-records-list">
            {selectedRecords.map(record => {
              const categories = storage.getCategories();
              const categoryInfo = categories.find(c => c.name === record.category) || {
                icon: '📝',
                color: '#6b7280',
              };
              
              return (
                <div key={record.id} className={`day-record-item ${record.type}`}>
                  <div className="day-record-left">
                    <span
                      className="day-record-icon"
                      style={{ backgroundColor: categoryInfo.color + '20', color: categoryInfo.color }}
                    >
                      {categoryInfo.icon}
                    </span>
                    <div>
                      <div className="day-record-category">{record.category}</div>
                      {record.note && <div className="day-record-note">{record.note}</div>}
                    </div>
                  </div>
                  <div className={`day-record-amount ${record.type}`}>
                    {record.type === 'expense' ? '-' : '+'}¥{record.amount.toFixed(2)}
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      )}

      {selectedDate && selectedRecords.length === 0 && (
        <div className="day-details empty">
          <p>{format(selectedDate, 'yyyy年MM月dd日', { locale: zhCN })} 没有记录</p>
        </div>
      )}
    </div>
  );
}

