package com.example.a7minuteworkoutapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {

    @Insert
    suspend fun insert(history: HistoryEntity)

    @Delete
    suspend fun delete(history: HistoryEntity)

    @Update
    suspend fun update(history: HistoryEntity)

    @Query(value = "SELECT * FROM `history`")
    fun getAllHistories() : Flow<List<HistoryEntity>>

    @Query(value = "SELECT * FROM `history` WHERE id =:id")
    fun getHistoryById() : Flow<HistoryEntity>
}