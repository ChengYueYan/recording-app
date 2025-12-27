package com.example.recording_app.ui.screen;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u001aD\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a;\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u00f8\u0001\u0000\u001aH\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001e"}, d2 = {"RecordItem", "", "record", "Lcom/example/recording_app/data/Record;", "onSwipeStart", "Lkotlin/Function0;", "onSwipe", "Lkotlin/Function1;", "", "onSwipeEnd", "RecordsScreen", "viewModel", "Lcom/example/recording_app/ui/viewmodel/FinanceViewModel;", "SummaryCard", "title", "", "amount", "", "icon", "gradient", "", "Landroidx/compose/ui/graphics/Color;", "modifier", "Landroidx/compose/ui/Modifier;", "SwipeableRecordItem", "isExpanded", "", "onExpandChanged", "onDelete", "onEdit", "recording-app_release"})
public final class RecordsScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void RecordsScreen(@org.jetbrains.annotations.NotNull
    com.example.recording_app.ui.viewmodel.FinanceViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SummaryCard(@org.jetbrains.annotations.NotNull
    java.lang.String title, double amount, @org.jetbrains.annotations.NotNull
    java.lang.String icon, @org.jetbrains.annotations.NotNull
    java.util.List<androidx.compose.ui.graphics.Color> gradient, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SwipeableRecordItem(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record, boolean isExpanded, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onExpandChanged, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void RecordItem(@org.jetbrains.annotations.NotNull
    com.example.recording_app.data.Record record, @org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function0<kotlin.Unit> onSwipeStart, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onSwipe, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onSwipeEnd) {
    }
}