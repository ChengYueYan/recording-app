package com.example.recording_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.recording_app.ui.components.EmptyState
import com.example.recording_app.ui.theme.*
import com.example.recording_app.ui.viewmodel.FinanceViewModel

@Composable
fun StatisticsScreen(viewModel: FinanceViewModel) {
    val records by viewModel.currentMonthRecords.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.statistics_analysis),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        val expenseRecords = records.filter { it.type == RecordType.EXPENSE }
        val incomeRecords = records.filter { it.type == RecordType.INCOME }

        if (records.isEmpty()) {
            item {
                EmptyState(
                    message = stringResource(id = R.string.no_data_this_month),
                    hint = stringResource(id = R.string.hint_add_records_for_stats),
                    icon = "📊"
                )
            }
        } else {
            // Expense Statistics
            if (expenseRecords.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.expense_category),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                    )
                }

                val expenseByCategory = expenseRecords.groupBy { it.category }
                    .mapValues { (_, records) -> records.sumOf { it.amount } }
                    .toList()
                    .sortedByDescending { it.second }

                val totalExpense = expenseRecords.sumOf { it.amount }

                items(expenseByCategory.size) { index ->
                    val (category, amount) = expenseByCategory[index]
                    CategoryStatItem(
                        category = category,
                        amount = amount,
                        total = totalExpense,
                        color = Danger,
                        icon = DefaultCategories.getAllCategories().find { it.name == category }?.icon ?: "📝"
                    )
                }
            }

            // Income Statistics
            if (incomeRecords.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.income_category),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                    )
                }

                val incomeByCategory = incomeRecords.groupBy { it.category }
                    .mapValues { (_, records) -> records.sumOf { it.amount } }
                    .toList()
                    .sortedByDescending { it.second }

                val totalIncome = incomeRecords.sumOf { it.amount }

                items(incomeByCategory.size) { index ->
                    val (category, amount) = incomeByCategory[index]
                    CategoryStatItem(
                        category = category,
                        amount = amount,
                        total = totalIncome,
                        color = Secondary,
                        icon = DefaultCategories.getAllCategories().find { it.name == category }?.icon ?: "📝"
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryStatItem(
    category: String,
    amount: Double,
    total: Double,
    color: Color,
    icon: String
) {
    val percentage = if (total > 0) (amount / total * 100) else 0.0
    val progress = (percentage / 100).coerceIn(0.0, 1.0).toFloat()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(color.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = icon, style = MaterialTheme.typography.titleLarge)
                    }
                    Column {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "${String.format("%.1f", percentage)}%",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
                Text(
                    text = "¥${String.format("%.2f", amount)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = color,
                trackColor = color.copy(alpha = 0.2f)
            )
        }
    }
}

