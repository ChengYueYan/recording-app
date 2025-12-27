package com.example.recording_app.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.res.stringResource
import com.example.recording_app.R
import com.example.recording_app.data.*
import com.example.recording_app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditRecordDialog(
    record: Record,
    onDismiss: () -> Unit,
    onConfirm: (Record) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0] ?: Locale.getDefault()
    val customColors = CustomTheme.colors
    val primaryColor = customColors.primary
    
    var selectedType by remember { mutableStateOf(record.type) }
    var amountText by remember { mutableStateOf(record.amount.toString()) }
    var selectedCategory by remember { mutableStateOf(record.category) }
    var noteText by remember { mutableStateOf(record.note ?: "") }
    var selectedDate by remember { mutableStateOf(record.date) }
    var showDatePicker by remember { mutableStateOf(false) }

    val categories = if (selectedType == RecordType.EXPENSE) {
        DefaultCategories.getExpenseCategories()
    } else {
        DefaultCategories.getIncomeCategories()
    }

    // Update category if type changes and current category is not in new list
    LaunchedEffect(selectedType) {
        if (!categories.any { it.name == selectedCategory }) {
            selectedCategory = categories.firstOrNull()?.name ?: ""
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(24.dp, shape = RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_record),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    IconButton(onClick = onDismiss) {
                        Text(
                            text = "✕",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextSecondary
                        )
                    }
                }

                // Type Selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TypeButton(
                        type = RecordType.EXPENSE,
                        label = stringResource(id = R.string.expense),
                        icon = "📉",
                        isSelected = selectedType == RecordType.EXPENSE,
                        onClick = { selectedType = RecordType.EXPENSE },
                        modifier = Modifier.weight(1f),
                        color = Danger
                    )
                    TypeButton(
                        type = RecordType.INCOME,
                        label = stringResource(id = R.string.income),
                        icon = "📈",
                        isSelected = selectedType == RecordType.INCOME,
                        onClick = { selectedType = RecordType.INCOME },
                        modifier = Modifier.weight(1f),
                        color = Secondary
                    )
                }

                // Amount Input
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { if (it.isEmpty() || it.all { char -> char.isDigit() || char == '.' }) amountText = it },
                    label = { Text(stringResource(id = R.string.amount)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = BorderLight
                    ),
                    prefix = { Text("¥", style = MaterialTheme.typography.titleMedium, color = TextSecondary) }
                )

                // Date Selection
                val dateDisplayFormat = remember(locale) {
                    if (locale.language == "zh") {
                        SimpleDateFormat("yyyy年MM月dd日 HH:mm", locale)
                    } else {
                        SimpleDateFormat("MMM dd, yyyy HH:mm", locale)
                    }
                }
                OutlinedTextField(
                    value = dateDisplayFormat.format(Date(selectedDate)),
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.date_time)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    readOnly = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = BorderLight
                    ),
                    trailingIcon = {
                        Text("📅", style = MaterialTheme.typography.bodyLarge)
                    }
                )

                // Simple Date Picker (using AlertDialog)
                if (showDatePicker) {
                    DatePickerDialog(
                        initialDate = selectedDate,
                        onDateSelected = { newDate ->
                            selectedDate = newDate
                            showDatePicker = false
                        },
                        onDismiss = { showDatePicker = false }
                    )
                }

                // Category Selection
                Text(
                    text = "选择分类",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { category ->
                        CategoryChip(
                            category = category,
                            isSelected = selectedCategory == category.name,
                            onClick = { selectedCategory = category.name }
                        )
                    }
                }

                // Note Input
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    label = { Text("备注（可选）") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = BorderLight
                    )
                )

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(stringResource(id = R.string.cancel), fontWeight = FontWeight.Medium)
                    }
                    Button(
                        onClick = {
                            val amount = amountText.toDoubleOrNull()
                            if (amount != null && amount > 0 && selectedCategory != null) {
                                val updatedRecord = record.copy(
                                    type = selectedType,
                                    amount = amount,
                                    category = selectedCategory!!,
                                    date = selectedDate,
                                    note = noteText.takeIf { it.isNotBlank() }
                                )
                                onConfirm(updatedRecord)
                            }
                        },
                        enabled = amountText.toDoubleOrNull()?.let { it > 0 } == true && selectedCategory != null,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(id = R.string.save), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun DatePickerDialog(
    initialDate: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = remember { Calendar.getInstance().apply { timeInMillis = initialDate } }
    var selectedYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var selectedMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var selectedDay by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }
    var selectedHour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("选择日期时间") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Date picker (simplified)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Year
                    Column {
                        Text(stringResource(id = R.string.year), style = MaterialTheme.typography.bodySmall)
                        OutlinedTextField(
                            value = selectedYear.toString(),
                            onValueChange = { selectedYear = it.toIntOrNull() ?: selectedYear },
                            modifier = Modifier.width(80.dp)
                        )
                    }
                    // Month
                    Column {
                        Text(stringResource(id = R.string.month), style = MaterialTheme.typography.bodySmall)
                        OutlinedTextField(
                            value = (selectedMonth + 1).toString(),
                            onValueChange = { 
                                val month = (it.toIntOrNull() ?: (selectedMonth + 1)) - 1
                                selectedMonth = month.coerceIn(0, 11)
                            },
                            modifier = Modifier.width(60.dp)
                        )
                    }
                    // Day
                    Column {
                        Text(stringResource(id = R.string.day), style = MaterialTheme.typography.bodySmall)
                        OutlinedTextField(
                            value = selectedDay.toString(),
                            onValueChange = { selectedDay = it.toIntOrNull() ?: selectedDay },
                            modifier = Modifier.width(60.dp)
                        )
                    }
                }
                // Time picker
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Hour
                    Column {
                        Text(stringResource(id = R.string.hour), style = MaterialTheme.typography.bodySmall)
                        OutlinedTextField(
                            value = selectedHour.toString(),
                            onValueChange = { selectedHour = (it.toIntOrNull() ?: selectedHour).coerceIn(0, 23) },
                            modifier = Modifier.width(60.dp)
                        )
                    }
                    // Minute
                    Column {
                        Text(stringResource(id = R.string.minute), style = MaterialTheme.typography.bodySmall)
                        OutlinedTextField(
                            value = selectedMinute.toString(),
                            onValueChange = { selectedMinute = (it.toIntOrNull() ?: selectedMinute).coerceIn(0, 59) },
                            modifier = Modifier.width(60.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newCalendar = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    onDateSelected(newCalendar.timeInMillis)
                }
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

