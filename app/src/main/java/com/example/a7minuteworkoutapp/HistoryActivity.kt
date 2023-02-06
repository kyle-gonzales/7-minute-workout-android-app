package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.a7minuteworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }

        val historyDao = (application as HistoryApp).db.historyDao()

        getFullHistory(historyDao)
    }

    private fun getFullHistory(historyDao: HistoryDao) {
        lifecycleScope.launch{
            historyDao.getAllHistories().collect() {history ->


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}