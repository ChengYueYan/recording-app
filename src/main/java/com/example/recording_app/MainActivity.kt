package com.example.recording_app

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

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: FinanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewModel = FinanceViewModel(application)
        
        setContent {
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
