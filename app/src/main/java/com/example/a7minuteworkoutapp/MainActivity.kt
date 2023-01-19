package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flStart : FrameLayout = findViewById(R.id.flStart)
        flStart.setOnClickListener{
            Toast.makeText(this, "Start!", Toast.LENGTH_SHORT).show()
        }
    }
}