import './TabNavigation.css';

interface TabNavigationProps {
  activeTab: 'records' | 'calendar' | 'statistics' | 'budget';
  onTabChange: (tab: 'records' | 'calendar' | 'statistics' | 'budget') => void;
}

export default function TabNavigation({ activeTab, onTabChange }: TabNavigationProps) {
  const tabs = [
    { id: 'records' as const, label: '📋 记录', name: '记录' },
    { id: 'calendar' as const, label: '📅 日历', name: '日历' },
    { id: 'statistics' as const, label: '📊 统计', name: '统计' },
    { id: 'budget' as const, label: '💵 预算', name: '预算' },
  ];

  return (
    <div className="tab-navigation">
      {tabs.map(tab => (
        <button
          key={tab.id}
          className={`tab-btn ${activeTab === tab.id ? 'active' : ''}`}
          onClick={() => onTabChange(tab.id)}
        >
          {tab.label}
        </button>
      ))}
    </div>
  );
}

