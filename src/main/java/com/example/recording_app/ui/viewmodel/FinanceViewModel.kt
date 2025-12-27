package com.example.recording_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recording_app.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val recordDao = database.recordDao()
    private val budgetDao = database.budgetDao()

    private val _selectedMonth = MutableStateFlow(getCurrentMonth())
    val selectedMonth: StateFlow<String> = _selectedMonth.asStateFlow()

    fun setSelectedMonth(month: String) {
        _selectedMonth.value = month
    }

    val allRecords: StateFlow<List<Record>> = recordDao.getAllRecords()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val currentMonthRecords: StateFlow<List<Record>> = combine(
        allRecords,
        selectedMonth
    ) { records, month ->
        val (start, end) = getMonthRange(month)
        records.filter { it.date >= start && it.date < end }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val currentMonthBudget: StateFlow<Budget?> = selectedMonth.flatMapLatest { month ->
        budgetDao.getBudgetByMonth(month)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val summary: StateFlow<Summary> = currentMonthRecords.map { records ->
        val expense = records.filter { it.type == RecordType.EXPENSE }.sumOf { it.amount }
        val income = records.filter { it.type == RecordType.INCOME }.sumOf { it.amount }
        Summary(income, expense, income - expense)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Summary(0.0, 0.0, 0.0)
    )

    fun insertRecord(record: Record) {
        viewModelScope.launch {
            recordDao.insertRecord(record)
        }
    }

    fun updateRecord(record: Record) {
        viewModelScope.launch {
            recordDao.updateRecord(record)
        }
    }

    fun deleteRecord(record: Record) {
        viewModelScope.launch {
            recordDao.deleteRecord(record)
        }
    }

    fun saveBudget(budget: Budget) {
        viewModelScope.launch {
            budgetDao.insertBudget(budget)
        }
    }

    private fun getCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        return String.format("%04d-%02d", year, month)
    }

    private fun getMonthRange(month: String): Pair<Long, Long> {
        val parts = month.split("-")
        val year = parts[0].toInt()
        val monthNum = parts[1].toInt()
        
        val calendar = Calendar.getInstance()
        calendar.set(year, monthNum - 1, 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val start = calendar.timeInMillis
        
        calendar.add(Calendar.MONTH, 1)
        val end = calendar.timeInMillis
        
        return Pair(start, end)
    }

    data class Summary(
        val income: Double,
        val expense: Double,
        val balance: Double
    )
}
