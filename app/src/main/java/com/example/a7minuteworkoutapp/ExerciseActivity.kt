package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding:ActivityExerciseBinding? = null
    // rest timer
    private var restTimer: CountDownTimer? = null
    private var restDuration: Long = 5000
    private var restPauseOffset: Long = 0
    private var restProgress = (restDuration/1000).toInt()
    private var restMaxProgress = (restDuration/1000).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // create action bar
        setSupportActionBar(binding?.tbExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbExercise?.setNavigationOnClickListener {
            onBackPressed() // clicking back button on device
        }

        binding?.tvTimer?.text = "${restDuration/1000}"
        startRestProgress(restPauseOffset)

    }

    private fun setRestProgress() {
        resetTimer()
        startRestProgress(restPauseOffset)
    }

    private fun resetTimer() {
        if (restTimer != null) {
            restTimer?.cancel()

            binding?.tvTimer?.text = "${restDuration/1000}"
            restPauseOffset = 0
        }
    }

    private fun startRestProgress(offset: Long) {
        binding?.progressBar?.max = restMaxProgress
        restProgress = (restDuration/1000).toInt()
        restTimer = object : CountDownTimer(restDuration - restPauseOffset, 1000) {
            override fun onTick(timeLeftinMillis: Long) {
                restPauseOffset = restDuration - timeLeftinMillis
                binding?.tvTimer?.text = "${timeLeftinMillis/1000}"
                binding?.progressBar?.progress = --restProgress
            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "finished!", Toast.LENGTH_SHORT).show()
            }
        }
        restTimer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        resetTimer()
        binding = null
    }
}