package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.a7minuteworkoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding : ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //set up tool bar
        setSupportActionBar(binding?.toolbar)
        if (supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbar?.setNavigationOnClickListener{
            finish()
//            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener{
            finish() // pop the finish activity from the activity stack
        }

        val historyDao = (application as HistoryApp).db.historyDao()

        insertDatetoDatabase(historyDao)
    }

    private fun insertDatetoDatabase(historyDao : HistoryDao) {

        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())

        val date = sdf.format(dateTime)
        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Toast.makeText(this@FinishActivity, "workout added to history", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}