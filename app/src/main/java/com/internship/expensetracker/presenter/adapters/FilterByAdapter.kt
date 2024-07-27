package com.internship.expensetracker.presenter.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.databinding.FilterByItemBinding

class FilterByAdapter(): RecyclerView.Adapter<FilterByAdapter.FilterByViewHolder>() {
    private var list: List<String> = listOf()
    private val filterList: MutableList<View>  = mutableListOf()
    private val sortList: MutableList<View> = mutableListOf()

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
        Log.d("debugging", filterList.size.toString())
        holder.itemView.setOnClickListener {
            when(holder.binding.tvFilterItem.text) {
                "Income", "Expense", "Transfer" -> filterBy(holder)
                "Highest", "Lowest", "Newest", "Oldest" -> sortBy(holder)
            }
        }
    }

    fun updateUi(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    private fun sortBy(holder: FilterByViewHolder) {
        if (holder.itemView.isSelected) {
            sortList[0].isSelected = false
            sortList.removeAt(0)
        } else {
            if (sortList.isEmpty()) {
                sortList.add(holder.itemView)
            } else {
                sortList[0].isSelected = false
                sortList[0] = holder.itemView
            }
            sortList[0].isSelected = true
        }
    }

    private fun filterBy(holder: FilterByViewHolder) {
        if (holder.itemView.isSelected) {
            filterList[0].isSelected = false
            filterList.removeAt(0)
        } else {
            if (filterList.isEmpty()) {
                filterList.add(holder.itemView)
            } else {
                filterList[0].isSelected = false
                filterList[0] = holder.itemView
            }
            filterList[0].isSelected = true
        }
    }
    fun resetList() {
        if (filterList.isNotEmpty()) {
            filterList[0].isSelected = false
            filterList.clear()
        }
        if (sortList.isNotEmpty()) {
            sortList[0].isSelected = false
            sortList.clear()
        }
    }

    fun getFilterValue(): String {
        return if (filterList.isNotEmpty()) {
            val filterValue = filterList[0] as TextView
            filterValue.text.toString()
        } else {
            ""
        }
    }

    fun getSortValue(): String {
        return if (sortList.isNotEmpty()) {
            val sortValue = sortList[0] as TextView
            sortValue.text.toString()
        } else {
            ""
        }
    }
}