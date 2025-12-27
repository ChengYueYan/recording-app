package com.example.recording_app.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.Stable
import com.example.recording_app.R
import com.example.recording_app.data.*
import com.example.recording_app.ui.components.EmptyState
import com.example.recording_app.ui.theme.*
import com.example.recording_app.ui.viewmodel.FinanceViewModel
import com.example.recording_app.util.CategoryLocalization
import kotlin.math.roundToInt
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RecordsScreen(viewModel: FinanceViewModel) {
    val summary by viewModel.summary.collectAsState()
    val records by viewModel.currentMonthRecords.collectAsState()
    var editingRecord by remember { mutableStateOf<Record?>(null) }
    var expandedRecordId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Summary Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    title = stringResource(id = R.string.income),
                    amount = summary.income,
                    icon = "📈",
                    gradient = listOf(Secondary, SecondaryLight),
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = stringResource(id = R.string.expense),
                    amount = summary.expense,
                    icon = "📉",
                    gradient = listOf(Danger, Color(0xFFFF8A80)),
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = stringResource(id = R.string.balance),
                    amount = summary.balance,
                    icon = "💰",
                    gradient = if (summary.balance >= 0) listOf(Secondary, SecondaryLight) else listOf(Danger, Color(0xFFFF8A80)),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Records List Header
        item {
            Text(
                text = stringResource(id = R.string.records_detail),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = CustomTheme.colors.primary,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Records List
        if (records.isEmpty()) {
            item {
                EmptyState(
                    message = stringResource(id = R.string.no_records),
                    hint = stringResource(id = R.string.hint_add_first_record)
                )
            }
        } else {
            items(records) { record ->
                SwipeableRecordItem(
                    record = record,
                    isExpanded = expandedRecordId == record.id,
                    onExpandChanged = { isExpanded ->
                        expandedRecordId = if (isExpanded) record.id else null
                    },
                    onDelete = { viewModel.deleteRecord(record) },
                    onEdit = { editingRecord = record }
                )
            }
        }
    }

    // Edit Dialog
    editingRecord?.let { record ->
        EditRecordDialog(
            record = record,
            onDismiss = { editingRecord = null },
            onConfirm = { updatedRecord ->
                viewModel.updateRecord(updatedRecord)
                editingRecord = null
            }
        )
    }
}

@Composable
fun SummaryCard(
    title: String,
    amount: Double,
    icon: String,
    gradient: List<Color>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = gradient[0].copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradient.map { it.copy(alpha = 0.1f) }
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "¥${String.format("%.2f", amount)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = gradient[0]
                )
            }
        }
    }
}

@Composable
fun SwipeableRecordItem(
    record: Record,
    isExpanded: Boolean,
    onExpandChanged: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val density = LocalDensity.current
    val swipeThreshold = with(density) { 60.dp.toPx() }
    val maxSwipeOffset = with(density) { 168.dp.toPx() } // 80dp * 2 + 8dp spacing
    
    // Use simple state for offset, no spring animation
    var offsetX by remember(record.id) { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    
    // Sync with expanded state
    LaunchedEffect(isExpanded) {
        if (!isDragging) {
            offsetX = if (isExpanded) -maxSwipeOffset else 0f
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Background actions (Edit and Delete buttons) - positioned on the left
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.End
        ) {
            // Delete button (leftmost)
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .background(Danger, RoundedCornerShape(16.dp))
                    .clickable { 
                        onDelete()
                        onExpandChanged(false)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete),
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        stringResource(id = R.string.delete),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Edit button
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .background(Secondary, RoundedCornerShape(16.dp))
                    .clickable { 
                        onEdit()
                        onExpandChanged(false)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        stringResource(id = R.string.edit),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        // Main content card - slides to the right when swiped left
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offsetX.coerceIn(-maxSwipeOffset, 0f).roundToInt(), 0) }
        ) {
            RecordItem(
                record = record,
                onSwipeStart = {
                    isDragging = true
                },
                onSwipe = { delta ->
                    // dragAmount: negative when swiping left, positive when swiping right
                    // When swiping left (delta < 0), we want offsetX to become more negative
                    // When swiping right (delta > 0), we want offsetX to become less negative (closer to 0)
                    offsetX = (offsetX + delta).coerceIn(-maxSwipeOffset, 0f)
                },
                onSwipeEnd = {
                    isDragging = false
                    if (offsetX < -swipeThreshold) {
                        // Swiped left enough, expand
                        offsetX = -maxSwipeOffset
                        onExpandChanged(true)
                    } else {
                        // Not enough swipe or swiped right, collapse
                        offsetX = 0f
                        onExpandChanged(false)
                    }
                }
            )
        }
    }
}

@Composable
fun RecordItem(
    record: Record,
    onSwipeStart: (() -> Unit)? = null,
    onSwipe: (Float) -> Unit,
    onSwipeEnd: () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0] ?: Locale.getDefault()
    val categories = DefaultCategories.getAllCategories()
    val category = categories.find { it.name == record.category }
    val dateFormat = remember(locale) {
        if (locale.language == "zh") {
            SimpleDateFormat("MM月dd日 HH:mm", locale)
        } else {
            SimpleDateFormat("MMM dd, HH:mm", locale)
        }
    }
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(record.id) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        onSwipeStart?.invoke()
                    },
                    onDragEnd = {
                        onSwipeEnd()
                    }
                ) { _, dragAmount ->
                    // dragAmount is negative when swiping left, positive when swiping right
                    onSwipe(dragAmount)
                }
            }
            .shadow(2.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(category?.color ?: 0xFF6B7280),
                                    Color(category?.color ?: 0xFF6B7280).copy(alpha = 0.7f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category?.icon ?: "📝",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = CategoryLocalization.getLocalizedCategoryName(context, record.category),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dateFormat.format(Date(record.date)),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    if (!record.note.isNullOrEmpty() && expanded) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = record.note,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            maxLines = 2
                        )
                    }
                }
            }

            // Amount
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${if (record.type == RecordType.EXPENSE) "-" else "+"}¥${String.format("%.2f", record.amount)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (record.type == RecordType.EXPENSE) Danger else Secondary
                )
            }
        }
    }
}
