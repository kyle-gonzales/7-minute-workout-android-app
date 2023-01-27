package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val flStart : FrameLayout = findViewById(R.id.flStart) use view binding instead

        binding?.flStart?.setOnClickListener{
            Toast.makeText(this, "Start!", Toast.LENGTH_SHORT).show()
            val exerciseIntent = Intent(this, ExerciseActivity::class.java)
            startActivity(exerciseIntent)
//            finish() // go back to main activity
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}