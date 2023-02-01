package com.example.a7minuteworkoutapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkoutapp.databinding.RecyclerviewExerciseBinding

class ExerciseAdapter(private val workout: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {


    // holder binds the item of the recyclerView to the model
    inner class ExerciseHolder(private val binding : RecyclerviewExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItems(exercise: Exercise) {
            binding.tvItem.text = "${exercise.id}"

            when {
                exercise.isSelected -> {
                    binding.tvItem.background = ContextCompat.getDrawable(this.itemView.context, R.drawable.item_circular_color_accent_border)
                    binding.tvItem.setTextColor(ContextCompat.getColor(this.itemView.context, R.color.primary_green))
                }
                exercise.isCompleted -> {
                    binding.tvItem.background = ContextCompat.getDrawable(this.itemView.context, R.drawable.item_circular_color_accent_background)
                    binding.tvItem.setTextColor(ContextCompat.getColor(this.itemView.context, R.color.white))
                }
                else -> {
                    binding.tvItem.background = ContextCompat.getDrawable(this.itemView.context, R.drawable.item_circular_grey_background)
                    binding.tvItem.setTextColor(ContextCompat.getColor(this.itemView.context, R.color.primary_text_grey))
                }
            }


        }
    }

    // returns the view holder; inflates the recyclerView with viewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        return ExerciseHolder(RecyclerviewExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // specifies how the adapter gets the data from the model's list
    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        val exercise = workout[position]

        holder.bindItems(exercise)

    }

    override fun getItemCount(): Int {
        return workout.size
    }

}