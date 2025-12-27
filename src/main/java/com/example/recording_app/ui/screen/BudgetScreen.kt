package com.example.recording_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.recording_app.R
import com.example.recording_app.data.*
import com.example.recording_app.ui.theme.*
import com.example.recording_app.ui.viewmodel.FinanceViewModel

@Composable
fun BudgetScreen(viewModel: FinanceViewModel) {
    val budget by viewModel.currentMonthBudget.collectAsState()
    val records by viewModel.currentMonthRecords.collectAsState()
    val month by viewModel.selectedMonth.collectAsState()

    var expectedExpense by remember(budget?.expectedExpense) {
        mutableStateOf(budget?.expectedExpense?.toString() ?: "0")
    }
    var expectedIncome by remember(budget?.expectedIncome) {
        mutableStateOf(budget?.expectedIncome?.toString() ?: "0")
    }

    LaunchedEffect(budget) {
        expectedExpense = budget?.expectedExpense?.toString() ?: "0"
        expectedIncome = budget?.expectedIncome?.toString() ?: "0"
    }

    val actualExpense = records.filter { it.type == RecordType.EXPENSE }.sumOf { it.amount }
    val actualIncome = records.filter { it.type == RecordType.INCOME }.sumOf { it.amount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.budget_management),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Budget Input Card
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
                OutlinedTextField(
                    value = expectedExpense,
                    onValueChange = { if (it.isEmpty() || it.all { char -> char.isDigit() || char == '.' }) expectedExpense = it },
                    label = { Text(stringResource(id = R.string.expected_expense)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Danger,
                        unfocusedBorderColor = BorderLight
                    ),
                    prefix = { Text("¥", style = MaterialTheme.typography.bodyLarge, color = TextSecondary) }
                )

                OutlinedTextField(
                    value = expectedIncome,
                    onValueChange = { if (it.isEmpty() || it.all { char -> char.isDigit() || char == '.' }) expectedIncome = it },
                    label = { Text(stringResource(id = R.string.expected_income)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Secondary,
                        unfocusedBorderColor = BorderLight
                    ),
                    prefix = { Text("¥", style = MaterialTheme.typography.bodyLarge, color = TextSecondary) }
                )

                Button(
                    onClick = {
                        val expense = expectedExpense.toDoubleOrNull() ?: 0.0
                        val income = expectedIncome.toDoubleOrNull() ?: 0.0
                        viewModel.saveBudget(Budget(month, expense, income))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(id = R.string.save_budget), fontWeight = FontWeight.Bold)
                }
            }
        }

        // Budget Progress
        val expectedExp = budget?.expectedExpense ?: 0.0
        val expectedInc = budget?.expectedIncome ?: 0.0

        if (expectedExp > 0) {
            BudgetProgressItem(
                label = stringResource(id = R.string.expected_expense),
                icon = "📉",
                actual = actualExpense,
                expected = expectedExp,
                color = Danger
            )
        }

        if (expectedInc > 0) {
            BudgetProgressItem(
                label = stringResource(id = R.string.expected_income),
                icon = "📈",
                actual = actualIncome,
                expected = expectedInc,
                color = Secondary
            )
        }
    }
}

@Composable
fun BudgetProgressItem(
    label: String,
    icon: String,
    actual: Double,
    expected: Double,
    color: Color
) {
    val progress = (actual / expected).coerceIn(0.0, 1.0).toFloat()
    val isOver = actual > expected

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(color, color.copy(alpha = 0.7f))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = icon, style = MaterialTheme.typography.headlineSmall)
                    }
                    Column {
                        Text(
                            label,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            "${String.format("%.1f", progress * 100)}%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        "¥${String.format("%.2f", actual)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (isOver) Danger else color
                    )
                    Text(
                        "/ ¥${String.format("%.2f", expected)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = if (isOver) Danger else color,
                trackColor = color.copy(alpha = 0.2f)
            )

            if (isOver) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "⚠️",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "已超出预算 ${String.format("%.2f", actual - expected)} 元",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Danger
                    )
                }
            }
        }
    }
}
