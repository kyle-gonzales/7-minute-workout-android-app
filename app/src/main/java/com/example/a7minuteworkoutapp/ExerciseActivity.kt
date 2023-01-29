package com.example.a7minuteworkoutapp

import android.annotation.SuppressLint
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
    private var restDuration: Long = 2000
    private var restPauseOffset: Long = 0
    private var restProgress = (restDuration/1000).toInt()
    private var restMaxProgress = (restDuration/1000).toInt()

    //exercise timer
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseDuration: Long = 3000
    private var exercisePauseOffset : Long = 0
    private var exerciseProgress = (exerciseDuration/1000).toInt()
    private var exerciseMaxProgress = (exerciseDuration/1000).toInt()

    //workout
    private var workoutList : ArrayList<Exercise> = Constants.getDefaultWorkout()
    private var exerciseIndex : Int = 0

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
    
    @SuppressLint("SetTextI18n")
    private fun setRestTimer() {
        binding?.tvNextExercise?.text = workoutList[exerciseIndex].name //!index error after last exercise
        binding?.tvExercise?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivExercise?.visibility = View.GONE

        binding?.tvNextExerciseLabel?.visibility = View.VISIBLE
        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvRest?.visibility = View.VISIBLE
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
                setExerciseTimer()
            }
        }
        restTimer?.start()
    }

    private fun setExerciseTimer(){
        binding?.tvExercise?.text = workoutList[exerciseIndex].name
        binding?.tvRest?.visibility = View.INVISIBLE
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvNextExerciseLabel?.visibility = View.INVISIBLE
        binding?.tvNextExercise?.visibility = View.INVISIBLE

        binding?.tvExercise?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivExercise?.visibility = View.VISIBLE
        binding?.ivExercise?.setImageResource(workoutList[exerciseIndex].img)

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
                if (exerciseIndex+1 < workoutList.size){
                    exerciseIndex++
                    setRestTimer()
                } else {
                    Toast.makeText(this@ExerciseActivity, "workout finished", Toast.LENGTH_SHORT).show()
                }
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