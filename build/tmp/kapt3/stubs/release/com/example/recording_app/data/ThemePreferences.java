package com.example.recording_app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0012\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eJ\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0018\u0010\u001d\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0004J\u0016\u0010 \u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006\""}, d2 = {"Lcom/example/recording_app/data/ThemePreferences;", "", "()V", "DEFAULT_PRIMARY_COLOR", "", "KEY_BACKGROUND_IMAGE", "", "KEY_ICON_IMAGE", "KEY_PRIMARY_COLOR", "KEY_SELECTED_ICON", "PREFS_NAME", "clearBackgroundImage", "", "context", "Landroid/content/Context;", "clearIconImage", "getBackgroundImagePath", "getIconImagePath", "getPrimaryColor", "getPrimaryColorAsColor", "Landroidx/compose/ui/graphics/Color;", "getPrimaryColorAsColor-vNxB06k", "(Landroid/content/Context;)J", "getSelectedIcon", "getSharedPreferences", "Landroid/content/SharedPreferences;", "saveBackgroundImageUri", "uri", "Landroid/net/Uri;", "saveIconImageUri", "savePrimaryColor", "color", "saveSelectedIcon", "icon", "recording-app_release"})
public final class ThemePreferences {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "theme_prefs";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_PRIMARY_COLOR = "primary_color";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_BACKGROUND_IMAGE = "background_image_path";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_ICON_IMAGE = "icon_image_path";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_SELECTED_ICON = "selected_icon";
    private static final long DEFAULT_PRIMARY_COLOR = 4284704497L;
    @org.jetbrains.annotations.NotNull
    public static final com.example.recording_app.data.ThemePreferences INSTANCE = null;
    
    private ThemePreferences() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.content.SharedPreferences getSharedPreferences(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void savePrimaryColor(@org.jetbrains.annotations.NotNull
    android.content.Context context, long color) {
    }
    
    public final long getPrimaryColor(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String saveBackgroundImageUri(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getBackgroundImagePath(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void clearBackgroundImage(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String saveIconImageUri(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getIconImagePath(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void clearIconImage(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    public final void saveSelectedIcon(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String icon) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getSelectedIcon(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
}