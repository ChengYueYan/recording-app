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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recording_app.ui.theme.*
import com.example.recording_app.data.ThemePreferences
import com.example.recording_app.util.IconManager

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
            "💰" to "金币",
            "📱" to "手机",
            "🎨" to "画笔",
            "⭐" to "星星",
            "🌸" to "樱花",
            "🎯" to "目标",
            "💎" to "钻石",
            "🎭" to "面具",
            "🌟" to "星芒",
            "🎪" to "帐篷",
            "🔮" to "水晶球",
            "💫" to "流星"
        )
    }
    
    var selectedIcon by remember { 
        mutableStateOf(ThemePreferences.getSelectedIcon(context) ?: "💰")
    }
    
    val backgroundPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val savedPath = ThemePreferences.saveBackgroundImageUri(context, it)
            if (savedPath != null) {
                backgroundImagePath = savedPath
                showBackgroundSuccess = true
                // Notify MainActivity to update background
                onBackgroundImageChanged?.invoke(savedPath)
            }
        }
    }
    
    var selectedPrimaryColor by remember { mutableStateOf(currentPrimary) }
    var showColorInfo by remember { mutableStateOf(false) }
    
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
            text = "个性化设置",
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
                    text = "应用图标",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "选择你喜欢的图标样式",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                // Icon Selection Grid
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    iconOptions.forEach { (icon, name) ->
                        IconOption(
                            icon = icon,
                            name = name,
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
                        Text("应用图标更改，重启应用生效", fontWeight = FontWeight.Bold)
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
                                text = "⚠️ 图标更改失败，请检查权限或稍后重试",
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
                                text = "重启应用",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        text = {
                            Text(
                                text = "图标已更改成功！请重启应用后，桌面上的应用图标将更新为新选择的图标。",
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
                                Text("确定重启", fontWeight = FontWeight.Bold)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showRestartDialog = false }
                            ) {
                                Text("稍后重启")
                            }
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                }
                
                Text(
                    text = "💡 提示：选择图标后点击上方按钮应用更改，然后重启应用即可看到新的桌面图标。",
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
                            text = "应用背景",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "选择自定义背景图片",
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
                        Text("选择背景图片", fontWeight = FontWeight.Bold)
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
                            Text("清除背景", fontWeight = FontWeight.Bold)
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
                            text = "✓ 背景图片已保存！背景会立即更新。",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
                
                Text(
                    text = "💡 背景图片会应用在整个应用界面，建议使用高质量图片",
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
                    text = "主题颜色",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "选择应用主色调",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                // Color Picker Grid
                val colors = listOf(
                    Color(0xFF6366F1) to "靛蓝",
                    Color(0xFF8B5CF6) to "紫色",
                    Color(0xFFEC4899) to "粉色",
                    Color(0xFFEF4444) to "红色",
                    Color(0xFFF59E0B) to "琥珀",
                    Color(0xFF10B981) to "翠绿",
                    Color(0xFF3B82F6) to "蓝色",
                    Color(0xFF06B6D4) to "青色",
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    colors.forEach { (color, name) ->
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
                    Text("应用主题颜色", fontWeight = FontWeight.Bold)
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
                    text = "💡 使用提示",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "• 图标选择用于个性化显示\n• 背景图片建议使用高质量图片以获得最佳效果\n• 主题颜色更改后立即生效\n• 背景图片更改后会自动更新并显示",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
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
                contentDescription = "已选择",
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
                        contentDescription = "已选择",
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
