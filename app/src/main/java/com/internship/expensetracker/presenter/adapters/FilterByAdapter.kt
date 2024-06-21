package com.internship.expensetracker.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.databinding.FilterByItemBinding

class FilterByAdapter(private val list: List<String>):
    RecyclerView.Adapter<FilterByAdapter.FilterByViewHolder>() {
        var isSelected = false

    class FilterByViewHolder(val binding: FilterByItemBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterByViewHolder {
        val binding = FilterByItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return FilterByViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FilterByViewHolder, position: Int) {
        holder.binding.tvFilterItem.text = list[position]
        holder.itemView.setOnClickListener {
            if (holder.itemView.isSelected && isSelected) {
                holder.itemView.isSelected = false
                isSelected = false
            }
            else {
                holder.itemView.isSelected = true
                isSelected = true
            }
        }
    }

}