package com.example.recording_app.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.recording_app.R
import com.example.recording_app.data.*
import com.example.recording_app.ui.theme.*
import com.example.recording_app.ui.viewmodel.FinanceViewModel
import com.example.recording_app.util.CategoryLocalization
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CalendarScreen(viewModel: FinanceViewModel) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0] ?: Locale.getDefault()
    val allRecords by viewModel.allRecords.collectAsState()
    val month by viewModel.selectedMonth.collectAsState()
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    val calendar = remember(month) {
        Calendar.getInstance().apply {
            val monthParts = month.split("-")
            set(Calendar.YEAR, monthParts[0].toInt())
            set(Calendar.MONTH, monthParts[1].toInt() - 1)
            set(Calendar.DAY_OF_MONTH, 1)
        }
    }

    val monthName = remember(month, locale) {
        val monthParts = month.split("-")
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, monthParts[0].toInt())
            set(Calendar.MONTH, monthParts[1].toInt() - 1)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        if (locale.language == "zh") {
            SimpleDateFormat("yyyy年MM月", locale).format(cal.time)
        } else {
            SimpleDateFormat("MMMM yyyy", locale).format(cal.time)
        }
    }

    val todayStart = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    // Get records for the displayed month
    val monthParts = month.split("-")
    val (year, monthNum) = monthParts[0].toInt() to monthParts[1].toInt()
    val monthStart = Calendar.getInstance().apply {
        set(year, monthNum - 1, 1, 0, 0, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
    val monthEnd = Calendar.getInstance().apply {
        set(year, monthNum - 1, 1, 0, 0, 0)
        set(Calendar.MILLISECOND, 0)
        add(Calendar.MONTH, 1)
    }.timeInMillis
    
    val monthRecords = remember(allRecords, month) {
        allRecords.filter { it.date >= monthStart && it.date < monthEnd }
    }

    // Calculate selected date records outside LazyColumn
    val selectedDateRecordList = remember(allRecords, selectedDate) {
        selectedDate?.let { date ->
            val dayCalendar = Calendar.getInstance().apply {
                timeInMillis = date
            }
            val dayStart = Calendar.getInstance().apply {
                set(dayCalendar.get(Calendar.YEAR), dayCalendar.get(Calendar.MONTH), dayCalendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            
            val dayEnd = dayStart + 86400000
            
            allRecords.filter { 
                it.date >= dayStart && it.date < dayEnd
            }
        } ?: emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Month Navigation Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                val prevCalendar = Calendar.getInstance().apply {
                                    set(year, monthNum - 2, 1)
                                }
                                val prevMonth = String.format("%04d-%02d", prevCalendar.get(Calendar.YEAR), prevCalendar.get(Calendar.MONTH) + 1)
                                viewModel.setSelectedMonth(prevMonth)
                                selectedDate = null
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(PrimaryContainer)
                        ) {
                            Icon(
                                Icons.Default.ChevronLeft,
                                contentDescription = "上一月",
                                tint = Primary
                            )
                        }

                        Text(
                            text = monthName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        IconButton(
                            onClick = {
                                val nextCalendar = Calendar.getInstance().apply {
                                    set(year, monthNum, 1)
                                }
                                val nextMonth = String.format("%04d-%02d", nextCalendar.get(Calendar.YEAR), nextCalendar.get(Calendar.MONTH) + 1)
                                viewModel.setSelectedMonth(nextMonth)
                                selectedDate = null
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(PrimaryContainer)
                        ) {
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = stringResource(id = R.string.next_month),
                                tint = Primary
                            )
                        }
                    }
                }
            }

            // Calendar Grid
            item {
                CalendarGrid(
                    calendar = calendar,
                    records = monthRecords,
                    todayStart = todayStart,
                    selectedDate = selectedDate,
                    onDateClick = { selectedDate = it }
                )
            }
        }

        // Selected day details - outside LazyColumn to avoid state issues
        if (selectedDate != null && selectedDateRecordList.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    val dateFormat = remember(locale) {
                        if (locale.language == "zh") {
                            SimpleDateFormat("yyyy年MM月dd日", locale)
                        } else {
                            SimpleDateFormat("MMMM dd, yyyy", locale)
                        }
                    }
                    Text(
                        text = dateFormat.format(Date(selectedDate!!)),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.heightIn(max = 300.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(selectedDateRecordList) { record ->
                            DayRecordItem(record = record)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarGrid(
    calendar: Calendar,
    records: List<Record>,
    todayStart: Long,
    selectedDate: Long?,
    onDateClick: (Long) -> Unit
) {
    val monthStart = Calendar.getInstance().apply {
        timeInMillis = calendar.timeInMillis
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    
    val monthEnd = Calendar.getInstance().apply {
        timeInMillis = monthStart.timeInMillis
        add(Calendar.MONTH, 1)
        add(Calendar.DAY_OF_MONTH, -1)
    }

    val startCalendar = Calendar.getInstance().apply {
        timeInMillis = monthStart.timeInMillis
        val dayOfWeek = get(Calendar.DAY_OF_WEEK)
        val daysToMonday = when (dayOfWeek) {
            Calendar.SUNDAY -> 6
            else -> dayOfWeek - Calendar.MONDAY
        }
        add(Calendar.DAY_OF_MONTH, -daysToMonday)
    }

    val endCalendar = Calendar.getInstance().apply {
        timeInMillis = monthEnd.timeInMillis
        val endDayOfWeek = get(Calendar.DAY_OF_WEEK)
        val daysToSunday = when (endDayOfWeek) {
            Calendar.SUNDAY -> 0
            else -> Calendar.SUNDAY - endDayOfWeek
        }
        add(Calendar.DAY_OF_MONTH, daysToSunday)
    }

    val days = remember(calendar.timeInMillis) {
        mutableListOf<Long>().apply {
            var current = Calendar.getInstance().apply {
                timeInMillis = startCalendar.timeInMillis
            }
            val endTime = endCalendar.timeInMillis
            while (current.timeInMillis <= endTime) {
                val dayStart = Calendar.getInstance().apply {
                    timeInMillis = current.timeInMillis
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                add(dayStart)
                current.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Weekday headers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(
                    stringResource(id = R.string.weekday_mon),
                    stringResource(id = R.string.weekday_tue),
                    stringResource(id = R.string.weekday_wed),
                    stringResource(id = R.string.weekday_thu),
                    stringResource(id = R.string.weekday_fri),
                    stringResource(id = R.string.weekday_sat),
                    stringResource(id = R.string.weekday_sun)
                ).forEach { day ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary
                        )
                    }
                }
            }

            // Calendar days
            days.chunked(7).forEach { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    week.forEach { dayTime ->
                        val dayCalendar = Calendar.getInstance().apply { timeInMillis = dayTime }
                        val dayStart = dayTime
                        val dayEnd = dayStart + 86400000
                        
                        val dayRecords = records.filter { 
                            it.date >= dayStart && it.date < dayEnd
                        }
                        
                        CalendarDay(
                            dayTime = dayTime,
                            dayCalendar = dayCalendar,
                            isCurrentMonth = dayCalendar.get(Calendar.MONTH) == monthStart.get(Calendar.MONTH),
                            isToday = dayTime >= todayStart && dayTime < todayStart + 86400000,
                            isSelected = selectedDate != null && dayTime >= selectedDate!! && dayTime < selectedDate!! + 86400000,
                            records = dayRecords,
                            onClick = { onDateClick(dayTime) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDay(
    dayTime: Long,
    dayCalendar: Calendar,
    isCurrentMonth: Boolean,
    isToday: Boolean,
    isSelected: Boolean,
    records: List<Record>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val expense = records.filter { it.type == RecordType.EXPENSE }.sumOf { it.amount }
    val income = records.filter { it.type == RecordType.INCOME }.sumOf { it.amount }
    val hasRecords = expense > 0.0 || income > 0.0
    
    val scale by animateFloatAsState(if (isSelected) 0.9f else 1f)

    val backgroundColor = when {
        isSelected -> Primary
        isToday -> PrimaryContainer
        else -> Color.Transparent
    }

    val textColor = when {
        isSelected -> Color.White
        isCurrentMonth -> TextPrimary
        else -> TextTertiary
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(enabled = isCurrentMonth, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = dayCalendar.get(Calendar.DAY_OF_MONTH).toString(),
                fontSize = 14.sp,
                fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
                color = textColor
            )

            if (hasRecords) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 2.dp)
                ) {
                    if (expense > 0.0) {
                        Text(
                            text = "¥${expense.toInt()}",
                            fontSize = 8.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.9f) else Danger,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                    }
                    if (income > 0.0) {
                        Text(
                            text = "+¥${income.toInt()}",
                            fontSize = 8.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.9f) else Secondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DayRecordItem(record: Record) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0] ?: Locale.getDefault()
    val categories = DefaultCategories.getAllCategories()
    val category = categories.find { it.name == record.category }
    val dateFormat = remember(locale) {
        SimpleDateFormat("HH:mm", locale)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
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
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column {
                    Text(
                        text = CategoryLocalization.getLocalizedCategoryName(context, record.category),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = dateFormat.format(Date(record.date)),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    if (!record.note.isNullOrEmpty()) {
                        Text(
                            text = record.note,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }
            Text(
                text = "${if (record.type == RecordType.EXPENSE) "-" else "+"}¥${String.format("%.2f", record.amount)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (record.type == RecordType.EXPENSE) Danger else Secondary
            )
        }
    }
}
