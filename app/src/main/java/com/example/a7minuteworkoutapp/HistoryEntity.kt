package com.example.a7minuteworkoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val date : String,
)
