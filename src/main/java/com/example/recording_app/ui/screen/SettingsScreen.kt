package com.example.recording_app.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recording_app.R
import com.example.recording_app.ui.theme.*
import com.example.recording_app.data.ThemePreferences
import com.example.recording_app.util.IconManager
import com.example.recording_app.util.LanguageManager
import com.example.recording_app.ui.components.LanguageSelectionDialog
import android.content.Intent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    onThemeColorChanged: ((Long) -> Unit)? = null,
    onBackgroundImageChanged: ((String?) -> Unit)? = null
) {
    val context = LocalContext.current
    val customColors = com.example.recording_app.ui.theme.CustomTheme.colors
    val currentPrimary = customColors.primary
    
    var backgroundImagePath by remember { 
        mutableStateOf<String?>(ThemePreferences.getBackgroundImagePath(context))
    }
    var showBackgroundSuccess by remember { mutableStateOf(false) }
    
    // Available icon options (anime-style emoji icons)
    val iconOptions = remember {
        listOf(
            "💰" to R.string.icon_gold,
            "📱" to R.string.icon_phone,
            "🎨" to R.string.icon_brush,
            "⭐" to R.string.icon_star,
            "🌸" to R.string.icon_sakura,
            "🎯" to R.string.icon_target,
            "💎" to R.string.icon_diamond,
            "🎭" to R.string.icon_mask,
            "🌟" to R.string.icon_star_glow,
            "🎪" to R.string.icon_tent,
            "🔮" to R.string.icon_crystal,
            "💫" to R.string.icon_meteor
        )
    }
    
    var selectedIcon by remember { 
        mutableStateOf(ThemePreferences.getSelectedIcon(context) ?: "💰")
    }

    val backgroundPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            android.util.Log.d("SettingsScreen", "Selected URI: $uri")

            // 🔧 添加持久化权限
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                android.util.Log.w("SettingsScreen", "Cannot take persistable permission", e)
            }

            val savedPath = ThemePreferences.saveBackgroundImageUri(context, it)
            android.util.Log.d("SettingsScreen", "Saved path: $savedPath")

            if (savedPath != null) {
                backgroundImagePath = savedPath
                showBackgroundSuccess = true
                // Notify MainActivity to update background
                onBackgroundImageChanged?.invoke(savedPath)
            } else {
                // 🔧 显示错误提示
                android.widget.Toast.makeText(
                    context,
                    "保存图片失败,请重试",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    var selectedPrimaryColor by remember { mutableStateOf(currentPrimary) }
    var showColorInfo by remember { mutableStateOf(false) }
    
    // Language settings
    var currentLanguage by remember { 
        mutableStateOf(LanguageManager.getSavedLanguage(context))
    }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showRestartLanguageDialog by remember { mutableStateOf(false) }
    
    // Update selected color when theme changes externally
    LaunchedEffect(currentPrimary) {
        selectedPrimaryColor = currentPrimary
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.personalization),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // App Icon Setting
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_icon),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = stringResource(id = R.string.select_icon_style),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                // Icon Selection Grid
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    iconOptions.forEach { (icon, nameResId) ->
                        IconOption(
                            icon = icon,
                            name = stringResource(id = nameResId),
                            isSelected = selectedIcon == icon,
                            onClick = {
                                selectedIcon = icon
                            }
                        )
                    }
                }
                
                var showRestartDialog by remember { mutableStateOf(false) }
                var iconChangeResult by remember { mutableStateOf<Boolean?>(null) }
                val currentSavedIcon = ThemePreferences.getSelectedIcon(context)
                
                if (selectedIcon != currentSavedIcon) {
                    Button(
                        onClick = {
                            val success = IconManager.changeAppIcon(context, selectedIcon)
                            iconChangeResult = success
                            if (success) {
                                ThemePreferences.saveSelectedIcon(context, selectedIcon)
                                showRestartDialog = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Secondary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(id = R.string.apply_icon_change_restart), fontWeight = FontWeight.Bold)
                    }
                }
                
                iconChangeResult?.let { success ->
                    if (!success) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Danger.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.icon_change_failed),
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
                
                if (showRestartDialog) {
                    AlertDialog(
                        onDismissRequest = { showRestartDialog = false },
                        title = {
                            Text(
                                text = stringResource(id = R.string.restart_app),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(id = R.string.icon_restart_confirmation),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showRestartDialog = false
                                    android.os.Process.killProcess(android.os.Process.myPid())
                                }
                            ) {
                                Text(stringResource(id = R.string.restart_now), fontWeight = FontWeight.Bold)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showRestartDialog = false }
                            ) {
                                Text(stringResource(id = R.string.restart_later))
                            }
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                }
                
                Text(
                    text = stringResource(id = R.string.icon_selection_tip),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Background Image Setting
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.app_background),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = stringResource(id = R.string.select_background_image),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { backgroundPickerLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = customColors.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(id = R.string.select_background), fontWeight = FontWeight.Bold)
                    }
                    
                    if (backgroundImagePath != null) {
                        Button(
                            onClick = { 
                                ThemePreferences.clearBackgroundImage(context)
                                backgroundImagePath = null
                                // Notify MainActivity to clear background
                                onBackgroundImageChanged?.invoke(null)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Danger,
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(id = R.string.clear_background), fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                if (showBackgroundSuccess) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Secondary.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.background_saved),
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
                
                Text(
                    text = stringResource(id = R.string.background_hint),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Theme Color Setting
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.theme_color),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = stringResource(id = R.string.select_theme_color),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                // Color Picker Grid
                val colors = remember {
                    listOf(
                        Color(0xFF6366F1) to R.string.color_indigo,
                        Color(0xFF8B5CF6) to R.string.color_purple,
                        Color(0xFFEC4899) to R.string.color_pink,
                        Color(0xFFEF4444) to R.string.color_red,
                        Color(0xFFF59E0B) to R.string.color_amber,
                        Color(0xFF10B981) to R.string.color_green,
                        Color(0xFF3B82F6) to R.string.color_blue,
                        Color(0xFF06B6D4) to R.string.color_cyan,
                    )
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    colors.forEach { (color, nameResId) ->
                        ColorOption(
                            color = color,
                            isSelected = selectedPrimaryColor == color,
                            onClick = { 
                                selectedPrimaryColor = color
                            }
                        )
                    }
                }

                Button(
                    onClick = { 
                        try {
                            // Convert Color to Long (ARGB)
                            val colorValue = (selectedPrimaryColor.alpha * 255).toInt().shl(24) or
                                ((selectedPrimaryColor.red * 255).toInt().shl(16)) or
                                ((selectedPrimaryColor.green * 255).toInt().shl(8)) or
                                (selectedPrimaryColor.blue * 255).toInt()
                            
                            // Save theme color
                            ThemePreferences.savePrimaryColor(context, colorValue.toLong())
                            // Notify MainActivity to update theme
                            onThemeColorChanged?.invoke(colorValue.toLong())
                            showColorInfo = true
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = selectedPrimaryColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(id = R.string.apply_theme_color), fontWeight = FontWeight.Bold)
                }
                
                if (showColorInfo) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = selectedPrimaryColor.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "主题颜色已应用！",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        // Language Setting
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.language_settings),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = stringResource(id = R.string.change_language),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                // Current Language Display
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = stringResource(id = R.string.current_language),
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = LanguageManager.getLanguageDisplayName(context, currentLanguage),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                }

                // Language Selection Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { 
                            if (currentLanguage != LanguageManager.LANGUAGE_CHINESE) {
                                LanguageManager.saveLanguage(context, LanguageManager.LANGUAGE_CHINESE)
                                currentLanguage = LanguageManager.LANGUAGE_CHINESE
                                showRestartLanguageDialog = true
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = if (currentLanguage == LanguageManager.LANGUAGE_CHINESE) {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = customColors.primaryContainer
                            )
                        } else {
                            ButtonDefaults.outlinedButtonColors()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.language_chinese_button),
                            fontWeight = if (currentLanguage == LanguageManager.LANGUAGE_CHINESE) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    }
                    
                    OutlinedButton(
                        onClick = { 
                            if (currentLanguage != LanguageManager.LANGUAGE_ENGLISH) {
                                LanguageManager.saveLanguage(context, LanguageManager.LANGUAGE_ENGLISH)
                                currentLanguage = LanguageManager.LANGUAGE_ENGLISH
                                showRestartLanguageDialog = true
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = if (currentLanguage == LanguageManager.LANGUAGE_ENGLISH) {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = customColors.primaryContainer
                            )
                        } else {
                            ButtonDefaults.outlinedButtonColors()
                        }
                    ) {
                        Text(
                            text = "English",
                            fontWeight = if (currentLanguage == LanguageManager.LANGUAGE_ENGLISH) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    }
                }
                
                Text(
                    text = stringResource(id = R.string.language_restart_hint),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.usage_tips),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = stringResource(id = R.string.tips_content),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }

    // Language Restart Dialog
    if (showRestartLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showRestartLanguageDialog = false },
            title = {
                Text(
                    text = stringResource(id = R.string.restart_app),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.restart_confirmation),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRestartLanguageDialog = false
                        // Restart app
                        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.restart_now),
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showRestartLanguageDialog = false }
                ) {
                    Text(stringResource(id = R.string.restart_later))
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(color)
            .shadow(
                elevation = if (isSelected) 8.dp else 2.dp,
                shape = CircleShape,
                spotColor = if (isSelected) color else Color.Gray
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(id = R.string.selected),
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun IconOption(
    icon: String,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) {
                        Brush.linearGradient(
                            colors = listOf(
                                CustomTheme.colors.primary,
                                CustomTheme.colors.primaryLight
                            )
                        )
                    } else {
                        Brush.linearGradient(
                            colors = listOf(SurfaceVariant, SurfaceVariant)
                        )
                    }
                )
                .shadow(
                    elevation = if (isSelected) 8.dp else 2.dp,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f))
                        .clip(CircleShape),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.selected),
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(4.dp)
                    )
                }
            }
        }
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = if (isSelected) CustomTheme.colors.primary else TextSecondary,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
