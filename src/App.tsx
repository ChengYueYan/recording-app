import { useState, useEffect } from 'react';
import { Record } from './types';
import { storage } from './utils/storage';
import { getCurrentMonth } from './utils/date';
import Header from './components/Header';
import SummaryCard from './components/SummaryCard';
import TabNavigation from './components/TabNavigation';
import RecordList from './components/RecordList';
import CalendarView from './components/CalendarView';
import Statistics from './components/Statistics';
import BudgetView from './components/BudgetView';
import AddRecordModal from './components/AddRecordModal';
import './App.css';

function App() {
  const [records, setRecords] = useState<Record[]>([]);
  const [currentMonth, setCurrentMonth] = useState(getCurrentMonth());
  const [activeTab, setActiveTab] = useState<'records' | 'calendar' | 'statistics' | 'budget'>('records');
  const [showAddModal, setShowAddModal] = useState(false);
  const [editingRecord, setEditingRecord] = useState<Record | null>(null);

  useEffect(() => {
    loadRecords();
  }, []);

  const loadRecords = () => {
    const allRecords = storage.getRecords();
    setRecords(allRecords);
  };

  const handleAddRecord = (record: Record) => {
    if (editingRecord) {
      // 更新记录
      const updated = records.map(r => r.id === record.id ? record : r);
      storage.saveRecords(updated);
      setRecords(updated);
      setEditingRecord(null);
    } else {
      // 添加新记录
      storage.addRecord(record);
      loadRecords();
    }
    setShowAddModal(false);
  };

  const handleDeleteRecord = (id: string) => {
    if (window.confirm('确定要删除这条记录吗？')) {
      storage.deleteRecord(id);
      loadRecords();
    }
  };

  const handleEditRecord = (record: Record) => {
    setEditingRecord(record);
    setShowAddModal(true);
  };

  const handleMonthChange = (month: string) => {
    setCurrentMonth(month);
  };

  return (
    <div className="app">
      <Header currentMonth={currentMonth} onMonthChange={handleMonthChange} />
      
      <div className="app-content">
        <SummaryCard records={records} month={currentMonth} />
        
        <TabNavigation activeTab={activeTab} onTabChange={setActiveTab} />
        
        <div className="tab-content">
          {activeTab === 'records' && (
            <RecordList
              records={records}
              month={currentMonth}
              onDelete={handleDeleteRecord}
              onEdit={handleEditRecord}
            />
          )}
          
          {activeTab === 'calendar' && (
            <CalendarView
              records={records}
              month={currentMonth}
              onMonthChange={handleMonthChange}
            />
          )}
          
          {activeTab === 'statistics' && (
            <Statistics records={records} month={currentMonth} />
          )}
          
          {activeTab === 'budget' && (
            <BudgetView month={currentMonth} />
          )}
        </div>
      </div>

      <button
        className="fab"
        onClick={() => {
          setEditingRecord(null);
          setShowAddModal(true);
        }}
        title="添加记录"
      >
        +
      </button>

      {showAddModal && (
        <AddRecordModal
          record={editingRecord}
          onClose={() => {
            setShowAddModal(false);
            setEditingRecord(null);
          }}
          onSave={handleAddRecord}
        />
      )}
    </div>
  );
}

export default App;

