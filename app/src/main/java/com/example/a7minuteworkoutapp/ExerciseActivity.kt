package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding:ActivityExerciseBinding? = null
    // rest timer
    private var restTimer: CountDownTimer? = null
    private var restDuration: Long = 3000
    private var restPauseOffset: Long = 0
    private var restProgress = (restDuration/1000).toInt()
    private var restMaxProgress = (restDuration/1000).toInt()

    //exercise timer
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseDuration: Long = 5000
    private var exercisePauseOffset : Long = 0
    private var exerciseProgress = (exerciseDuration/1000).toInt()
    private var exerciseMaxProgress = (exerciseDuration/1000).toInt()

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

        setRestTimer()
    }
    
    private fun setRestTimer() {
        binding?.tvTitle?.text = "TAKE A REST"
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.flRestView?.visibility = View.VISIBLE

        resetTimer()
        startRestProgress(restPauseOffset)
    }

    private fun resetTimer() {
        if (restTimer != null) {
            restTimer?.cancel()

            binding?.tvRestTimer?.text = "${restDuration/1000}"
            restPauseOffset = 0
        }
    }

    private fun startRestProgress(offset: Long) {
        binding?.progressBarRest?.max = restMaxProgress
        restProgress = (restDuration/1000).toInt()
        restTimer = object : CountDownTimer(restDuration - restPauseOffset, 1000) {
            override fun onTick(timeLeftinMillis: Long) {
                restPauseOffset = restDuration - timeLeftinMillis
                binding?.tvRestTimer?.text = "${timeLeftinMillis/1000}"
                binding?.progressBarRest?.progress = --restProgress
            }
            override fun onFinish() {
//                Toast.makeText(this@ExerciseActivity, "finished!", Toast.LENGTH_SHORT).show()
                setExerciseTimer()
            }
        }
        restTimer?.start()
    }

    private fun setExerciseTimer(){
        binding?.tvTitle?.text = "PUSHUPS"
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE

        resetExerciseTimer()
        startExerciseProgress(exercisePauseOffset)
    }

    private fun resetExerciseTimer() {
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()

            binding?.tvExerciseTimer?.text = "${exerciseDuration/1000}"
            exercisePauseOffset = 0
        }
    }

    private fun startExerciseProgress(offset : Long) {
        binding?.progressBarExercise?.max = exerciseMaxProgress
        exerciseProgress = (exerciseDuration/1000).toInt()
        exerciseTimer = object : CountDownTimer(exerciseDuration - exercisePauseOffset, 1000) {
            override fun onTick(timeLeftInMillis: Long) {
                exercisePauseOffset = exerciseDuration - timeLeftInMillis
                binding?.tvExerciseTimer?.text = "${timeLeftInMillis/1000}"
                binding?.progressBarExercise?.progress = --exerciseProgress
            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "exercise finished", Toast.LENGTH_SHORT).show()
                setRestTimer()
            }
        }
        exerciseTimer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        resetTimer()
        resetExerciseTimer()
        binding = null
    }
}