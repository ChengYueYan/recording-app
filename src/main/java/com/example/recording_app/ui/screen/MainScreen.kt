package com.example.recording_app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.recording_app.R
import com.example.recording_app.ui.theme.*
import com.example.recording_app.ui.viewmodel.FinanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: FinanceViewModel,
    onThemeColorChanged: ((Long) -> Unit)? = null,
    onBackgroundImageChanged: ((String?) -> Unit)? = null,
    hasBackgroundImage: Boolean = false
) {
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }
    
    val customColors = com.example.recording_app.ui.theme.CustomTheme.colors
    val primaryColor = customColors.primary
    val primaryLight = customColors.primaryLight
    val primaryContainer = customColors.primaryContainer

    Scaffold(
        containerColor = if (hasBackgroundImage) Color.Transparent else Background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = primaryColor,
                modifier = Modifier
                    .size(64.dp)
                    .shadow(12.dp, shape = CircleShape)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_record),
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Surface,
                modifier = Modifier.shadow(8.dp, shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "📋",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (selectedTab == 0) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(primaryColor)
                                )
                            }
                        }
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.records),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryColor,
                        selectedTextColor = primaryColor,
                        indicatorColor = primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "📅",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (selectedTab == 1) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(primaryColor)
                                )
                            }
                        }
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.calendar),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryColor,
                        selectedTextColor = primaryColor,
                        indicatorColor = primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "⚙️",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (selectedTab == 2) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(primaryColor)
                                )
                            }
                        }
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.settings),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryColor,
                        selectedTextColor = primaryColor,
                        indicatorColor = primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "📊",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (selectedTab == 3) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(primaryColor)
                                )
                            }
                        }
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.statistics),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryColor,
                        selectedTextColor = primaryColor,
                        indicatorColor = primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "💵",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (selectedTab == 4) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(primaryColor)
                                )
                            }
                        }
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.budget),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selectedTab == 4) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryColor,
                        selectedTextColor = primaryColor,
                        indicatorColor = primaryContainer
                    )
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedVisibility(
                visible = selectedTab == 0,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                RecordsScreen(viewModel)
            }
            AnimatedVisibility(
                visible = selectedTab == 1,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                CalendarScreen(viewModel)
            }
            AnimatedVisibility(
                visible = selectedTab == 2,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                SettingsScreen(
                    onThemeColorChanged = onThemeColorChanged,
                    onBackgroundImageChanged = onBackgroundImageChanged
                )
            }
            AnimatedVisibility(
                visible = selectedTab == 3,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                StatisticsScreen(viewModel)
            }
            AnimatedVisibility(
                visible = selectedTab == 4,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                BudgetScreen(viewModel)
            }
        }
    }

    if (showAddDialog) {
        AddRecordDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { record ->
                viewModel.insertRecord(record)
                showAddDialog = false
            }
        )
    }
}
