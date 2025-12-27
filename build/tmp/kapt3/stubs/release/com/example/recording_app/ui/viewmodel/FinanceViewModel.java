package com.example.recording_app.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001+B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000bJ\b\u0010!\u001a\u00020\u0007H\u0002J\u001c\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020$0#2\u0006\u0010%\u001a\u00020\u0007H\u0002J\u000e\u0010&\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000bJ\u000e\u0010\'\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u0011J\u000e\u0010)\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u0007J\u000e\u0010*\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000bR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\rR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\rR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\r\u00a8\u0006,"}, d2 = {"Lcom/example/recording_app/ui/viewmodel/FinanceViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_selectedMonth", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "allRecords", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/example/recording_app/data/Record;", "getAllRecords", "()Lkotlinx/coroutines/flow/StateFlow;", "budgetDao", "Lcom/example/recording_app/data/BudgetDao;", "currentMonthBudget", "Lcom/example/recording_app/data/Budget;", "getCurrentMonthBudget", "currentMonthRecords", "getCurrentMonthRecords", "database", "Lcom/example/recording_app/data/AppDatabase;", "recordDao", "Lcom/example/recording_app/data/RecordDao;", "selectedMonth", "getSelectedMonth", "summary", "Lcom/example/recording_app/ui/viewmodel/FinanceViewModel$Summary;", "getSummary", "deleteRecord", "", "record", "getCurrentMonth", "getMonthRange", "Lkotlin/Pair;", "", "month", "insertRecord", "saveBudget", "budget", "setSelectedMonth", "updateRecord", "Summary", "recording-app_release"})
public final class FinanceViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.example.recording_app.data.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.recording_app.data.RecordDao recordDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.recording_app.data.BudgetDao budgetDao = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedMonth = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedMonth = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.recording_app.data.Record>> allRecords = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.recording_app.data.Record>> currentMonthRecords = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.example.recording_app.data.Budget> currentMonthBudget = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.example.recording_app.ui.viewmodel.FinanceViewModel.Summary> summary = null;
    
    public FinanceViewModel(@org.jetbrains.annotations.NotNull
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedMonth() {
        return null;
    }
    
    public final void setSelectedMonth(@org.jetbrains.annotations.NotNull
    java.lang.String month) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.recording_app.data.Record>> getAllRecords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.recording_app.data.Record>> getCurrentMonthRecords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.example.recording_app.data.Budget> getCurrentMonthBudget() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.example.recording_app.ui.viewmodel.FinanceViewModel.Summary> getSummary() {
        return null;
    }
    
    public final void insertRecord(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record) {
    }
    
    public final void updateRecord(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record) {
    }
    
    public final void deleteRecord(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record) {
    }
    
    public final void saveBudget(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Budget budget) {
    }
    
    private final java.lang.String getCurrentMonth() {
        return null;
    }
    
    private final kotlin.Pair<java.lang.Long, java.lang.Long> getMonthRange(java.lang.String month) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b\u00a8\u0006\u0016"}, d2 = {"Lcom/example/recording_app/ui/viewmodel/FinanceViewModel$Summary;", "", "income", "", "expense", "balance", "(DDD)V", "getBalance", "()D", "getExpense", "getIncome", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "recording-app_release"})
    public static final class Summary {
        private final double income = 0.0;
        private final double expense = 0.0;
        private final double balance = 0.0;
        
        public Summary(double income, double expense, double balance) {
            super();
        }
        
        public final double getIncome() {
            return 0.0;
        }
        
        public final double getExpense() {
            return 0.0;
        }
        
        public final double getBalance() {
            return 0.0;
        }
        
        public final double component1() {
            return 0.0;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.recording_app.ui.viewmodel.FinanceViewModel.Summary copy(double income, double expense, double balance) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}