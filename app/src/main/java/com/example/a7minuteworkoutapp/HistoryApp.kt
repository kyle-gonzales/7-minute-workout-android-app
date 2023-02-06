package com.example.a7minuteworkoutapp

import android.app.Application

class HistoryApp : Application() {
    
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}