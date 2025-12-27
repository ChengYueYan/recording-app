package com.example.recording_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class Record(
    @PrimaryKey
    val id: String,
    val type: RecordType,
    val amount: Double,
    val category: String,
    val date: Long, // timestamp
    val note: String? = null
)

enum class RecordType {
    EXPENSE,
    INCOME
}

