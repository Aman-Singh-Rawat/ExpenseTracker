package com.internship.expensetracker.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.data.models.BudgetItem
import com.internship.expensetracker.databinding.BudgetItemLayoutBinding

class BudgetAdapter: RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {
    private var budgetList: MutableList<BudgetItem> = mutableListOf()
    class BudgetViewHolder(val binding: BudgetItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = BudgetItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return BudgetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return budgetList.size
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.binding.apply {
            val budgetAchieve = budgetList[position].budgetAchieve
            val budgetMax = budgetList[position].budgetMax

            tvBudgetType.text = budgetList[position].budgetType
            tvBudgetOutOf.text = "$budgetAchieve of $budgetMax"
            budgetItemProgress.max = budgetMax
            budgetItemProgress.progress = budgetAchieve

            if (budgetAchieve > budgetMax) {
                tvExceedLimit.visibility = View.VISIBLE
                imgWarning.visibility = View.VISIBLE
                tvRemaining.text = "Remaining $0"
            } else {
                tvExceedLimit.visibility = View.GONE
                imgWarning.visibility = View.GONE

                tvRemaining.text = "Remaining ${budgetList[position].budgetAchieve}"
            }
        }
    }

    fun updateUi(budgetList: MutableList<BudgetItem>) {
        this.budgetList = budgetList
    }
}