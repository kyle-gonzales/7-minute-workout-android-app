package com.example.a7minuteworkoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkoutapp.databinding.RecyclerviewHistoryBinding

class HistoryAdapter(private val items: ArrayList<HistoryEntity>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(binding: RecyclerviewHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val clHistory = binding.clHistory
        val tvPosition = binding.tvPosition
        val tvDate = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(RecyclerviewHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        val context = holder.itemView.context
        val item = items[position]

        holder.tvPosition.text = (position + 1).toString() // position is zero-indexed
        holder.tvDate.text = item.date

        if (position % 2 == 0) {
            holder.clHistory.setBackgroundColor(ContextCompat.getColor(context, R.color.accent_grey))
        } else {
            holder.clHistory.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}