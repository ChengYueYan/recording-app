import { Record, MonthlyBudget, Category } from '../types';

const STORAGE_KEYS = {
  RECORDS: 'finance_records',
  BUDGETS: 'finance_budgets',
  CATEGORIES: 'finance_categories',
};

// 默认分类
const defaultCategories: Category[] = [
  { id: '1', name: '餐饮', icon: '🍔', color: '#ef4444', type: 'expense' },
  { id: '2', name: '交通', icon: '🚗', color: '#3b82f6', type: 'expense' },
  { id: '3', name: '购物', icon: '🛍️', color: '#8b5cf6', type: 'expense' },
  { id: '4', name: '娱乐', icon: '🎬', color: '#ec4899', type: 'expense' },
  { id: '5', name: '医疗', icon: '🏥', color: '#10b981', type: 'expense' },
  { id: '6', name: '教育', icon: '📚', color: '#f59e0b', type: 'expense' },
  { id: '7', name: '住房', icon: '🏠', color: '#6366f1', type: 'expense' },
  { id: '8', name: '其他', icon: '📝', color: '#6b7280', type: 'expense' },
  { id: '9', name: '工资', icon: '💰', color: '#10b981', type: 'income' },
  { id: '10', name: '奖金', icon: '🎁', color: '#f59e0b', type: 'income' },
  { id: '11', name: '投资', icon: '📈', color: '#3b82f6', type: 'income' },
  { id: '12', name: '其他收入', icon: '💵', color: '#6366f1', type: 'income' },
];

export const storage = {
  // 获取所有记录
  getRecords(): Record[] {
    try {
      const data = localStorage.getItem(STORAGE_KEYS.RECORDS);
      return data ? JSON.parse(data) : [];
    } catch {
      return [];
    }
  },

  // 保存记录
  saveRecords(records: Record[]): void {
    localStorage.setItem(STORAGE_KEYS.RECORDS, JSON.stringify(records));
  },

  // 添加记录
  addRecord(record: Record): void {
    const records = this.getRecords();
    records.push(record);
    this.saveRecords(records);
  },

  // 删除记录
  deleteRecord(id: string): void {
    const records = this.getRecords();
    const filtered = records.filter(r => r.id !== id);
    this.saveRecords(filtered);
  },

  // 获取预算
  getBudgets(): MonthlyBudget[] {
    try {
      const data = localStorage.getItem(STORAGE_KEYS.BUDGETS);
      return data ? JSON.parse(data) : [];
    } catch {
      return [];
    }
  },

  // 保存预算
  saveBudgets(budgets: MonthlyBudget[]): void {
    localStorage.setItem(STORAGE_KEYS.BUDGETS, JSON.stringify(budgets));
  },

  // 获取或创建指定月份的预算
  getOrCreateBudget(month: string): MonthlyBudget {
    const budgets = this.getBudgets();
    let budget = budgets.find(b => b.month === month);
    
    if (!budget) {
      budget = {
        month,
        expectedExpense: 0,
        expectedIncome: 0,
        records: [],
      };
      budgets.push(budget);
      this.saveBudgets(budgets);
    }
    
    return budget;
  },

  // 更新预算
  updateBudget(budget: MonthlyBudget): void {
    const budgets = this.getBudgets();
    const index = budgets.findIndex(b => b.month === budget.month);
    if (index >= 0) {
      budgets[index] = budget;
    } else {
      budgets.push(budget);
    }
    this.saveBudgets(budgets);
  },

  // 获取分类
  getCategories(): Category[] {
    try {
      const data = localStorage.getItem(STORAGE_KEYS.CATEGORIES);
      if (data) {
        return JSON.parse(data);
      } else {
        // 首次使用，保存默认分类
        localStorage.setItem(STORAGE_KEYS.CATEGORIES, JSON.stringify(defaultCategories));
        return defaultCategories;
      }
    } catch {
      return defaultCategories;
    }
  },

  // 保存分类
  saveCategories(categories: Category[]): void {
    localStorage.setItem(STORAGE_KEYS.CATEGORIES, JSON.stringify(categories));
  },

  // 添加分类
  addCategory(category: Category): void {
    const categories = this.getCategories();
    categories.push(category);
    this.saveCategories(categories);
  },
};

