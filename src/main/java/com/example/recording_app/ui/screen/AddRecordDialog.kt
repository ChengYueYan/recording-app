package com.example.recording_app.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import com.example.recording_app.R
import com.example.recording_app.data.*
import com.example.recording_app.ui.theme.*
import com.example.recording_app.util.CategoryLocalization
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddRecordDialog(
    onDismiss: () -> Unit,
    onConfirm: (Record) -> Unit
) {
    val customColors = CustomTheme.colors
    val primaryColor = customColors.primary
    
    var selectedType by remember { mutableStateOf(RecordType.EXPENSE) }
    var amountText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var noteText by remember { mutableStateOf("") }

    val categories = if (selectedType == RecordType.EXPENSE) {
        DefaultCategories.getExpenseCategories()
    } else {
        DefaultCategories.getIncomeCategories()
    }

    if (selectedCategory == null && categories.isNotEmpty()) {
        selectedCategory = categories.first().name
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
                        text = stringResource(id = R.string.add_record),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
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

                // Category Selection
                Text(
                    text = stringResource(id = R.string.select_category),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
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
                    label = { Text(stringResource(id = R.string.note_optional)) },
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
                                val record = Record(
                                    id = UUID.randomUUID().toString(),
                                    type = selectedType,
                                    amount = amount,
                                    category = selectedCategory!!,
                                    date = System.currentTimeMillis(),
                                    note = noteText.takeIf { it.isNotBlank() }
                                )
                                onConfirm(record)
                            }
                        },
                        enabled = amountText.toDoubleOrNull()?.let { it > 0 } == true && selectedCategory != null,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeButton(
    type: RecordType,
    label: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    val scale by animateFloatAsState(if (isSelected) 1f else 0.95f)
    
    Card(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = if (isSelected) 8.dp else 2.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) color.copy(alpha = 0.1f) else SurfaceVariant
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) color else TextSecondary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val scale by animateFloatAsState(if (isSelected) 1.05f else 1f)
    val localizedName = CategoryLocalization.getLocalizedCategoryName(context, category.name)
    
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = category.icon)
                Text(
                    text = localizedName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        },
        modifier = Modifier.scale(scale),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(category.color).copy(alpha = 0.2f),
            selectedLabelColor = Color(category.color),
            containerColor = SurfaceVariant,
            labelColor = TextSecondary
        ),
        shape = RoundedCornerShape(12.dp),
        border = FilterChipDefaults.filterChipBorder(
            selectedBorderColor = Color(category.color),
            borderColor = BorderLight,
            selectedBorderWidth = 2.dp,
            borderWidth = 1.dp
        )
    )
}
