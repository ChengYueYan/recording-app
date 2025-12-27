package com.example.recording_app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.recording_app.util.LanguageManager

@Composable
fun LanguageSelectionDialog(
    onLanguageSelected: (String) -> Unit
) {
    var selectedLanguage by remember { mutableStateOf(LanguageManager.LANGUAGE_CHINESE) }
    
    Dialog(onDismissRequest = { /* Prevent dismissing */ }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "选择语言 / Select Language",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "请选择您偏好的语言 / Please select your preferred language",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Chinese Option
                OutlinedButton(
                    onClick = { selectedLanguage = LanguageManager.LANGUAGE_CHINESE },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = if (selectedLanguage == LanguageManager.LANGUAGE_CHINESE) {
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    } else {
                        ButtonDefaults.outlinedButtonColors()
                    }
                ) {
                    Text(
                        text = "中文 (Chinese)",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (selectedLanguage == LanguageManager.LANGUAGE_CHINESE) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                }
                
                // English Option
                OutlinedButton(
                    onClick = { selectedLanguage = LanguageManager.LANGUAGE_ENGLISH },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = if (selectedLanguage == LanguageManager.LANGUAGE_ENGLISH) {
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    } else {
                        ButtonDefaults.outlinedButtonColors()
                    }
                ) {
                    Text(
                        text = "English",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (selectedLanguage == LanguageManager.LANGUAGE_ENGLISH) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Confirm Button
                Button(
                    onClick = { onLanguageSelected(selectedLanguage) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "确认 / Confirm",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

