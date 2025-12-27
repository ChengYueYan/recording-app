package com.example.recording_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.recording_app.data.ThemePreferences

@Immutable
data class CustomColorScheme(
    val primary: Color,
    val primaryLight: Color,
    val primaryDark: Color,
    val primaryContainer: Color
)

val LocalCustomColors = compositionLocalOf<CustomColorScheme> {
    // Default fallback colors
    CustomColorScheme(
        primary = Primary,
        primaryLight = PrimaryLight,
        primaryDark = PrimaryDark,
        primaryContainer = PrimaryContainer
    )
}

@Composable
fun FinanceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    primaryColor: Color? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val savedPrimaryColor = remember(context) { 
        try {
            ThemePreferences.getPrimaryColorAsColor(context)
        } catch (e: Exception) {
            Primary
        }
    }
    val currentPrimary = primaryColor ?: savedPrimaryColor

    // Generate color variants from primary color
    val primaryLight = remember(currentPrimary) {
        Color(
            red = (currentPrimary.red.coerceIn(0f, 1f) * 255 + 40).coerceIn(0f, 255f) / 255f,
            green = (currentPrimary.green.coerceIn(0f, 1f) * 255 + 40).coerceIn(0f, 255f) / 255f,
            blue = (currentPrimary.blue.coerceIn(0f, 1f) * 255 + 40).coerceIn(0f, 255f) / 255f,
            alpha = 1f
        )
    }
    val primaryDark = remember(currentPrimary) {
        Color(
            red = (currentPrimary.red.coerceIn(0f, 1f) * 255 - 30).coerceIn(0f, 255f) / 255f,
            green = (currentPrimary.green.coerceIn(0f, 1f) * 255 - 30).coerceIn(0f, 255f) / 255f,
            blue = (currentPrimary.blue.coerceIn(0f, 1f) * 255 - 30).coerceIn(0f, 255f) / 255f,
            alpha = 1f
        )
    }
    val primaryContainer = remember(currentPrimary) {
        Color(
            red = (currentPrimary.red.coerceIn(0f, 1f) * 0.15f + 0.95f).coerceIn(0f, 1f),
            green = (currentPrimary.green.coerceIn(0f, 1f) * 0.15f + 0.95f).coerceIn(0f, 1f),
            blue = (currentPrimary.blue.coerceIn(0f, 1f) * 0.15f + 0.95f).coerceIn(0f, 1f),
            alpha = 1f
        )
    }

    val customColors = CustomColorScheme(
        primary = currentPrimary,
        primaryLight = primaryLight,
        primaryDark = primaryDark,
        primaryContainer = primaryContainer
    )

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> lightColorScheme(
            primary = currentPrimary,
            onPrimary = Color.White,
            primaryContainer = primaryContainer,
            onPrimaryContainer = currentPrimary,
            secondary = Secondary,
            onSecondary = Color.White,
            tertiary = Info,
            onTertiary = Color.White,
            error = Danger,
            onError = Color.White,
            errorContainer = Color(0xFFFFE5E5),
            onErrorContainer = Danger,
            background = Background,
            onBackground = TextPrimary,
            surface = Surface,
            onSurface = TextPrimary,
            surfaceVariant = SurfaceVariant,
            onSurfaceVariant = TextSecondary,
            outline = BorderLight,
            outlineVariant = BorderMedium,
            scrim = Overlay,
            inverseSurface = TextPrimary,
            inverseOnSurface = Surface,
            inversePrimary = primaryLight,
        )
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = currentPrimary.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

object CustomTheme {
    val colors: CustomColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomColors.current
}
