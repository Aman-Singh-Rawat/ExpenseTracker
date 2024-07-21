package com.internship.expensetracker.data.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.databinding.MonthsItemBinding

class MonthsAdapter(private var monthsList: List<String>): RecyclerView.Adapter<MonthsAdapter.MonthsViewHolder>() {

    class MonthsViewHolder(val binding: MonthsItemBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthsViewHolder {
        val binding = MonthsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MonthsViewHolder(binding)
    }

    override fun getItemCount(): Int = monthsList.size

    override fun onBindViewHolder(holder: MonthsViewHolder, position: Int) {
        holder.binding.tvMonthName.text = monthsList[position]
    }
}