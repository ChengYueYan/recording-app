import { format, parseISO, addMonths, subMonths } from 'date-fns';
import { zhCN } from 'date-fns/locale';
import { storage } from '../utils/storage';
import { exportToCSV, exportToJSON } from '../utils/export';
import './Header.css';

interface HeaderProps {
  currentMonth: string;
  onMonthChange: (month: string) => void;
}

export default function Header({ currentMonth, onMonthChange }: HeaderProps) {
  const date = parseISO(currentMonth + '-01');
  const monthName = format(date, 'yyyy年MM月', { locale: zhCN });

  const handlePrevMonth = () => {
    const newDate = subMonths(date, 1);
    onMonthChange(format(newDate, 'yyyy-MM'));
  };

  const handleNextMonth = () => {
    const newDate = addMonths(date, 1);
    onMonthChange(format(newDate, 'yyyy-MM'));
  };

  const handleToday = () => {
    onMonthChange(format(new Date(), 'yyyy-MM'));
  };

  const handleExportCSV = () => {
    const records = storage.getRecords();
    exportToCSV(records);
  };

  const handleExportJSON = () => {
    const records = storage.getRecords();
    exportToJSON(records);
  };

  return (
    <header className="header">
      <div className="header-content">
        <h1 className="header-title">💰 记账App</h1>
        <div className="header-actions">
          <div className="month-navigator">
            <button className="nav-btn" onClick={handlePrevMonth}>‹</button>
            <span className="month-display" onClick={handleToday}>{monthName}</span>
            <button className="nav-btn" onClick={handleNextMonth}>›</button>
          </div>
          <div className="export-buttons">
            <button className="export-btn" onClick={handleExportCSV} title="导出为CSV">
              📥 CSV
            </button>
            <button className="export-btn" onClick={handleExportJSON} title="导出为JSON">
              📥 JSON
            </button>
          </div>
        </div>
      </div>
    </header>
  );
}

