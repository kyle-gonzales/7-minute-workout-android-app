package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null
    private var historyAdapter: HistoryAdapter? = null
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
            historyDao.getAllHistories().collect { history ->

                if (history.isNotEmpty()){
                    binding?.tvNoData?.visibility = View.INVISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity, LinearLayoutManager.VERTICAL, false)
                    historyAdapter = HistoryAdapter(ArrayList(history))
                    binding?.rvHistory?.adapter = historyAdapter

                } else {
                    binding?.rvHistory?.visibility = View.INVISIBLE
                    binding?.tvNoData?.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}