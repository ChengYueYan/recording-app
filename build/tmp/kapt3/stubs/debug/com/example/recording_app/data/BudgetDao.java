package com.example.recording_app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/example/recording_app/data/BudgetDao;", "", "getBudgetByMonth", "Lkotlinx/coroutines/flow/Flow;", "Lcom/example/recording_app/data/Budget;", "month", "", "insertBudget", "", "budget", "(Lcom/example/recording_app/data/Budget;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBudget", "recording-app_debug"})
@androidx.room.Dao
public abstract interface BudgetDao {
    
    @androidx.room.Query(value = "SELECT * FROM budgets WHERE month = :month")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.example.recording_app.data.Budget> getBudgetByMonth(@org.jetbrains.annotations.NotNull
    java.lang.String month);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertBudget(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Budget budget, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateBudget(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Budget budget, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}