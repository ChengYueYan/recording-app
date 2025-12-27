package com.example.recording_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey
    val month: String, // YYYY-MM format
    val expectedExpense: Double = 0.0,
    val expectedIncome: Double = 0.0
)

