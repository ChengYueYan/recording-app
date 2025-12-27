package com.example.recording_app.ui.screen;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aV\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u001aI\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0013\u001a\u00020\u00032\b\u0010\u0014\u001a\u0004\u0018\u00010\u00032\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0016H\u0007\u00a2\u0006\u0002\u0010\u0017\u001a\u0010\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\u001a\u0010\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\fH\u0007\u00a8\u0006\u001d"}, d2 = {"CalendarDay", "", "dayTime", "", "dayCalendar", "Ljava/util/Calendar;", "isCurrentMonth", "", "isToday", "isSelected", "records", "", "Lcom/example/recording_app/data/Record;", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "CalendarGrid", "calendar", "todayStart", "selectedDate", "onDateClick", "Lkotlin/Function1;", "(Ljava/util/Calendar;Ljava/util/List;JLjava/lang/Long;Lkotlin/jvm/functions/Function1;)V", "CalendarScreen", "viewModel", "Lcom/example/recording_app/ui/viewmodel/FinanceViewModel;", "DayRecordItem", "record", "recording-app_release"})
public final class CalendarScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void CalendarScreen(@org.jetbrains.annotations.NotNull
    com.example.recording_app.ui.viewmodel.FinanceViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CalendarGrid(@org.jetbrains.annotations.NotNull
    java.util.Calendar calendar, @org.jetbrains.annotations.NotNull
    java.util.List<com.example.recording_app.data.Record> records, long todayStart, @org.jetbrains.annotations.Nullable
    java.lang.Long selectedDate, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onDateClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CalendarDay(long dayTime, @org.jetbrains.annotations.NotNull
    java.util.Calendar dayCalendar, boolean isCurrentMonth, boolean isToday, boolean isSelected, @org.jetbrains.annotations.NotNull
    java.util.List<com.example.recording_app.data.Record> records, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DayRecordItem(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record) {
    }
}