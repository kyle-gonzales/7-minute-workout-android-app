package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNIT_VIEW = "US_UNIT_VIEW"
    }

    private var binding : ActivityBmiBinding? = null
    private var bmi : Double = 0.0
    private var weight : Double = 0.0
    private var height : Double = 0.0

    private var current_view = METRIC_UNIT_VIEW

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

        setMetricUnitView()

        binding?.btnCalculate?.setOnClickListener {

            if (isValidInput(current_view)){
                weight = binding?.etWeight?.text.toString().toDouble()
                height = binding?.etHeight?.text.toString().toDouble()

                displayBMIResults()
            } else {
                Toast.makeText(this@BmiActivity, "Input valid values", Toast.LENGTH_SHORT).show()
            }

        }

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rbMetricUnits) {
                setMetricUnitView()


                binding?.btnCalculate?.setOnClickListener {

                    if (isValidInput(current_view)){
                        weight = binding?.etWeight?.text.toString().toDouble()
                        height = binding?.etHeight?.text.toString().toDouble()

                        displayBMIResults()
                    } else {
                        Toast.makeText(this@BmiActivity, "Input valid values", Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                setUsUnitView()

                binding?.btnCalculate?.setOnClickListener {

                    if (isValidInput(current_view)){
                        weight = binding?.etPounds?.text.toString().toDouble() / 2.205
                        val feet = binding?.etFeet?.text.toString().toDouble()
                        val inches = binding?.etInch?.text.toString().toDouble()

                        height = (feet + (inches/12)) * 30.48

                        displayBMIResults()
                    } else {
                        Toast.makeText(this@BmiActivity, "Input valid values", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun displayBMIResults() {
        bmi = calculateBmi(weight, height)
        binding?.tvBmi?.text = bmi.toString()

        binding?.tvDiagnosis?.text = diagnoseBmi(bmi)
        binding?.tvMessage?.text = adviseBmi(bmi)
        binding?.llBmiResult?.visibility = View.VISIBLE
    }

    private fun isValidInput(view : String) : Boolean{
        return when (view) {
            METRIC_UNIT_VIEW -> {
                !(binding?.etWeight?.text.toString().isEmpty()|| binding?.etHeight?.text.toString().isEmpty())
            } US_UNIT_VIEW -> {
                !(binding?.etInch?.text.toString().isEmpty()|| binding?.etFeet?.text.toString().isEmpty()|| binding?.etPounds?.text.toString().isEmpty())
            }
            else -> {return false}
        }
    }
    private fun setMetricUnitView(){
        current_view = METRIC_UNIT_VIEW
        binding?.tilHeight?.visibility = View.VISIBLE
        binding?.tilWeight?.visibility = View.VISIBLE

        binding?.tilUsUnitWeight?.visibility = View.INVISIBLE
        binding?.tilUsUnitHeightFeet?.visibility = View.INVISIBLE
        binding?.tilUsUnitHeightInch?.visibility = View.INVISIBLE

        binding?.etFeet?.text!!.clear()
        binding?.etInch?.text!!.clear()
        binding?.etPounds?.text!!.clear()

        binding?.llBmiResult?.visibility = View.INVISIBLE
    }
   private fun setUsUnitView(){
        current_view = US_UNIT_VIEW
        binding?.tilHeight?.visibility = View.INVISIBLE
        binding?.tilWeight?.visibility = View.INVISIBLE

        binding?.tilUsUnitWeight?.visibility = View.VISIBLE
        binding?.tilUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilUsUnitHeightInch?.visibility = View.VISIBLE

        binding?.etHeight?.text!!.clear()
        binding?.etWeight?.text!!.clear()

        binding?.llBmiResult?.visibility = View.INVISIBLE
    }

    private fun calculateBmi(weight: Double, height: Double): Double {
        return try {
            String.format("%.2f", weight / (height / 100 * height / 100)).toDouble()
        } catch (e: ArithmeticException) {
            -1.0
        } catch (e: Exception) {
            -2.0
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
            in 0.0..18.49 -> "You are ugly af. hit the gym bitch"
            in 18.5..24.99 -> "I've seen skeletons with more muscle than you"
            in 25.0..29.9 -> "You are still ugly af. hit the gym bitch"
            in 30.0..Double.MAX_VALUE -> "damn"
            else -> "User input is invalid! Check your input and try again!"
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}

