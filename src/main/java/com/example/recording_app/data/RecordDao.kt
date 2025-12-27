package com.example.recording_app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<Record>>

    @Query("SELECT * FROM records WHERE date >= :startTimestamp AND date < :endTimestamp ORDER BY date DESC")
    fun getRecordsByMonth(startTimestamp: Long, endTimestamp: Long): Flow<List<Record>>

    @Query("SELECT * FROM records WHERE id = :id")
    suspend fun getRecordById(id: String): Record?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: Record)

    @Update
    suspend fun updateRecord(record: Record)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("SELECT SUM(amount) FROM records WHERE type = :type AND date >= :startTimestamp AND date < :endTimestamp")
    suspend fun getTotalByType(type: RecordType, startTimestamp: Long, endTimestamp: Long): Double?
}

