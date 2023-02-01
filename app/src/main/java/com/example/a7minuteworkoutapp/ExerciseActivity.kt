package com.example.a7minuteworkoutapp

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkoutapp.databinding.ActivityExerciseBinding
import com.example.a7minuteworkoutapp.databinding.RecyclerviewExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding? = null
    private var tts: TextToSpeech? = null
    private var ttsIsInit = false
    private var lowBeepPlayer: MediaPlayer? = null
    private var highBeepPlayer: MediaPlayer? = null

    // rest timer
    private var restTimer: CountDownTimer? = null
    private var restDuration: Long = 10000
    private var restPauseOffset: Long = 0
    private var restProgress = (restDuration/1000).toInt()
    private var restMaxProgress = (restDuration/1000).toInt()

    //exercise timer
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseDuration: Long = 30000
    private var exercisePauseOffset : Long = 0
    private var exerciseProgress = (exerciseDuration/1000).toInt()
    private var exerciseMaxProgress = (exerciseDuration/1000).toInt()

    //workout
    private var workoutList : ArrayList<Exercise> = Constants.getDefaultWorkout()
    private var exerciseIndex : Int = 0

    //recyclerView
    private var exerciseAdapter : ExerciseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        tts = TextToSpeech(this@ExerciseActivity, this@ExerciseActivity)

        // initializing the recycler view
        exerciseAdapter = ExerciseAdapter(workoutList)
        binding?.rvExercise?.adapter = exerciseAdapter
        binding?.rvExercise?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // create action bar
        setSupportActionBar(binding?.tbExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbExercise?.setNavigationOnClickListener {
            onBackPressed() // clicking back button on device
        }
        try {
            lowBeepPlayer = MediaPlayer.create(this, R.raw.low_beep_sound)
            highBeepPlayer = MediaPlayer.create(this, R.raw.high_beep_sound)
            lowBeepPlayer?.isLooping = false
            highBeepPlayer?.isLooping = false
        } catch(e : Exception) {
            Toast.makeText(this, "media player error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }


        Handler(Looper.getMainLooper()).postDelayed({
            setRestTimer() // TODO: add progress bar
//            Toast.makeText(this, "starting workout", Toast.LENGTH_SHORT).show()
        }, 1000)
    }
    private fun setRestTimer() {
        binding?.tvNextExercise?.text = workoutList[exerciseIndex].name
        binding?.tvExercise?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivExercise?.visibility = View.GONE

        binding?.tvNextExerciseLabel?.visibility = View.VISIBLE
        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvRest?.visibility = View.VISIBLE
        binding?.flRestView?.visibility = View.VISIBLE

//        player?.start()
        speakOut("Get ready for next exercise: ${workoutList[exerciseIndex].name}")

        resetRestTimer()
        startRestProgress(restPauseOffset)
    }

    private fun resetRestTimer() {
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
                binding?.progressBarRest?.progress = --restProgress
                if (restProgress in 1..3)
                    lowBeepPlayer?.start()
                else if (restProgress == 0)
                    highBeepPlayer?.start()
                binding?.tvRestTimer?.text = "$restProgress"
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                workoutList[exerciseIndex].isSelected = true
                exerciseAdapter?.notifyDataSetChanged() //
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

        speakOut("Start next exercise: ${workoutList[exerciseIndex].name}")

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
                binding?.progressBarExercise?.progress = --exerciseProgress
                if (exerciseProgress in 1..3)
                    lowBeepPlayer?.start()
                else if (exerciseProgress == 0)
                    highBeepPlayer?.start()
                binding?.tvExerciseTimer?.text = "$exerciseProgress"
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {

                workoutList[exerciseIndex].isSelected = false
                workoutList[exerciseIndex].isCompleted = true
                exerciseAdapter?.notifyDataSetChanged()
                if (exerciseIndex+1 < workoutList.size){
                    exerciseIndex++
                    setRestTimer()
                } else {
//                    Toast.makeText(this@ExerciseActivity, "workout finished", Toast.LENGTH_SHORT).show()
                    finish() // pop the exercise activity from the activity stack
                    val finishIntent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(finishIntent) // push the finish activity on the activity stack
                }
            }
        }
        exerciseTimer?.start()
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            }
            ttsIsInit = true
        } else {
            Toast.makeText(this, "TTS failed to initialize", Toast.LENGTH_SHORT).show()
        }
    }
    private fun speakOut(text: String) {
        if (ttsIsInit)
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        else
            Toast.makeText(this, "TTS init too slow", Toast.LENGTH_SHORT).show()

    }
    override fun onDestroy() {
        super.onDestroy()

        resetRestTimer()
        resetExerciseTimer()
        if (tts?.isSpeaking!!) {
            tts?.stop()
        }
        tts?.shutdown()

        if (lowBeepPlayer != null) {
            lowBeepPlayer?.stop()
        }
        lowBeepPlayer?.release()
        binding = null
    }
}