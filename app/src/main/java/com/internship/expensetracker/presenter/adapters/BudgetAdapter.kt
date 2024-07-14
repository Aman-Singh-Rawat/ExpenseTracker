package com.internship.expensetracker.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.data.models.Budget
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.BudgetItemLayoutBinding

class BudgetAdapter: RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {
    private var commonList: List<Budget> = mutableListOf()
    private var budgetList: List<Budget> = mutableListOf()
    private var transactionList: List<Transaction> = mutableListOf()
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

            val budgetAchieve = 0
            val budgetMax = budgetList[position].budgetMoney

            tvBudgetType.text = budgetList[position].budgetCategory
            tvBudgetOutOf.text = "$budgetAchieve of $budgetMax".removeSuffix(".0")
            budgetItemProgress.max = budgetMax.toInt()
            budgetItemProgress.progress = budgetAchieve

            if (budgetAchieve > budgetMax) {
                tvExceedLimit.visibility = View.VISIBLE
                imgWarning.visibility = View.VISIBLE
                tvRemaining.text = "Remaining $0"
            } else {
                tvExceedLimit.visibility = View.GONE
                imgWarning.visibility = View.GONE

                tvRemaining.text = "Remaining $0"
            }
        }
    }

    fun updateUi(budgetList: List<Budget>, transactionList: List<Transaction>) {
        this.budgetList = budgetList
        this.transactionList = transactionList
        getCommonList()
        notifyDataSetChanged()
    }

    private fun getCommonList() {
        val budgetCategories = budgetList.map { it.budgetCategory }.toSet()
        val transactionCategories = transactionList.map { it.category }.toSet()

        val commonBudget = budgetCategories.intersect(transactionCategories)
        commonBudget.map { category ->
            commonList = budgetList.filter {
                it.budgetCategory == category
            }
            println(commonList)
        }
    }
}