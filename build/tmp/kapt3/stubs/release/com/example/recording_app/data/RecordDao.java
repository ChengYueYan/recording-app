package com.example.recording_app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ$\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\'J+\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/example/recording_app/data/RecordDao;", "", "deleteRecord", "", "record", "Lcom/example/recording_app/data/Record;", "(Lcom/example/recording_app/data/Record;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllRecords", "Lkotlinx/coroutines/flow/Flow;", "", "getRecordById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecordsByMonth", "startTimestamp", "", "endTimestamp", "getTotalByType", "", "type", "Lcom/example/recording_app/data/RecordType;", "(Lcom/example/recording_app/data/RecordType;JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertRecord", "updateRecord", "recording-app_release"})
@androidx.room.Dao
public abstract interface RecordDao {
    
    @androidx.room.Query(value = "SELECT * FROM records ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.recording_app.data.Record>> getAllRecords();
    
    @androidx.room.Query(value = "SELECT * FROM records WHERE date >= :startTimestamp AND date < :endTimestamp ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.recording_app.data.Record>> getRecordsByMonth(long startTimestamp, long endTimestamp);
    
    @androidx.room.Query(value = "SELECT * FROM records WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRecordById(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.recording_app.data.Record> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertRecord(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateRecord(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteRecord(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(amount) FROM records WHERE type = :type AND date >= :startTimestamp AND date < :endTimestamp")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getTotalByType(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.RecordType type, long startTimestamp, long endTimestamp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
}