package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    private var binding : ActivityBmiBinding? = null
    private var bmi : Double = 0.0
    var weight : Double = 0.0
    var height : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }
        try{

        weight = binding?.etWeight?.text.toString().toDouble()
        height = binding?.etHeight?.text.toString().toDouble()
        } catch (e: Exception) {
            Log.e("EDIT", e.toString())
            Toast.makeText(this@BmiActivity, "error", Toast.LENGTH_SHORT).show()
        }

        binding?.btnCalculate?.setOnClickListener {
            bmi = calculateBmi(weight, height)
            binding?.tvBmi?.text = bmi.toString()
            binding?.tvDiagnosis?.text = diagnoseBmi(bmi)
            binding?.tvMessage?.text = adviseBmi(bmi)
            binding?.llBmiResult?.visibility = View.VISIBLE
        }
    }

    private fun calculateBmi(weight: Double, height: Double): Double{
        return try{
//            String.format(".%2d", weight / (height/100 * height/100)).toDouble()
            weight / (height/100 * height/100)
        } catch(e : ArithmeticException) {
            -1.0
        }
    }

    private fun diagnoseBmi(bmi: Double): String {

        return when(bmi){
            in 0.0..18.49 -> "Underweight"
            in 18.5..24.99 -> "Healthy weight"
            in 25.0..29.9 -> "Overweight"
            in 30.0..Double.MAX_VALUE -> "Obese"
            else -> "Invalid"
        }

    }

    private fun adviseBmi(bmi: Double): String{

        return when(bmi){
            in 0.0..18.49 -> "Oops! You should exercise and increase your food intake!"
            in 18.5..24.99 -> "You are doing great! Keep exercising to feel even better!"
            in 25.0..29.9 -> "Oops! You really need to take care of yourself! Workout maybe!"
            in 30.0..Double.MAX_VALUE -> "You need to take more serious action in taking care of your health!"
            else -> "User input is invalid! Check your input and try again!"
        }

    }

}

