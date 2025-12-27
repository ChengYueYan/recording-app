package com.example.recording_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.recording_app.ui.screen.MainScreen
import com.example.recording_app.ui.theme.FinanceAppTheme
import com.example.recording_app.ui.viewmodel.FinanceViewModel
import com.example.recording_app.data.ThemePreferences
import com.example.recording_app.ui.components.BackgroundImageBox
import com.example.recording_app.ui.components.LanguageSelectionDialog
import com.example.recording_app.util.LanguageManager
import java.io.File

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: FinanceViewModel

    override fun attachBaseContext(newBase: Context) {
        val savedLanguage = LanguageManager.getSavedLanguage(newBase)
        val context = LanguageManager.setAppLocale(newBase, savedLanguage)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Apply saved language
        val savedLanguage = LanguageManager.getSavedLanguage(this)
        LanguageManager.updateConfiguration(this, savedLanguage)
        
        viewModel = FinanceViewModel(application)

        setContent {
            var showLanguageDialog by remember {
                mutableStateOf(LanguageManager.isFirstLaunch(this@MainActivity))
            }

            var themeColor by remember {
                mutableStateOf(ThemePreferences.getPrimaryColorAsColor(applicationContext))
            }

            // 🔧 确保背景路径正确初始化
            var backgroundImagePath by remember {
                mutableStateOf<String?>(ThemePreferences.getBackgroundImagePath(applicationContext))
            }

            // 🔧 添加日志
            LaunchedEffect(Unit) {
                val path = ThemePreferences.getBackgroundImagePath(applicationContext)
                android.util.Log.d("MainActivity", "Initial background path: $path")
                if (path != null) {
                    val file = File(path)
                    android.util.Log.d("MainActivity", "File exists: ${file.exists()}, size: ${file.length()}")
                }
            }

            if (showLanguageDialog) {
                LanguageSelectionDialog(
                    onLanguageSelected = { language ->
                        LanguageManager.saveLanguage(this@MainActivity, language)
                        LanguageManager.setFirstLaunchCompleted(this@MainActivity)
                        showLanguageDialog = false
                        recreate()
                    }
                )
            }

            FinanceAppTheme(primaryColor = themeColor) {
                BackgroundImageBox(backgroundImagePath = backgroundImagePath) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = if (backgroundImagePath != null) {
                            Color.Transparent
                        } else {
                            MaterialTheme.colorScheme.background
                        }
                    ) {
                        MainScreen(
                            viewModel = viewModel,
                            onThemeColorChanged = { colorValue ->
                                themeColor = Color(colorValue)
                            },
                            onBackgroundImageChanged = { path ->
                                android.util.Log.d("MainActivity", "Background changed to: $path")
                                backgroundImagePath = path
                            },
                            hasBackgroundImage = backgroundImagePath != null
                        )
                    }
                }
            }
        }
    }
}

