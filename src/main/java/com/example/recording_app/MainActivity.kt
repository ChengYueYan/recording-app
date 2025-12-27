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
                try {
                    mutableStateOf(ThemePreferences.getPrimaryColorAsColor(applicationContext))
                } catch (e: Exception) {
                    mutableStateOf(com.example.recording_app.ui.theme.Primary)
                }
            }
            
            var backgroundImagePath by remember { 
                mutableStateOf<String?>(ThemePreferences.getBackgroundImagePath(applicationContext))
            }
            
            if (showLanguageDialog) {
                LanguageSelectionDialog(
                    onLanguageSelected = { language ->
                        LanguageManager.saveLanguage(this@MainActivity, language)
                        LanguageManager.setFirstLaunchCompleted(this@MainActivity)
                        showLanguageDialog = false
                        // Restart activity to apply language change
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
