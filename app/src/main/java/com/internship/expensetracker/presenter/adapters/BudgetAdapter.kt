package com.internship.expensetracker.presenter.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.Budget
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.BudgetItemLayoutBinding

class BudgetAdapter(val context: Context): RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {
    private var sumOfTransaction = 0.0
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

            viewsColorChange(holder.binding, budgetList[position])
            val budgetAchieve = 0
            val budgetMax = budgetList[position].budgetMoney

            tvBudgetType.text = budgetList[position].budgetCategory
            tvBudgetOutOf.text = "$budgetAchieve of $budgetMax".removeSuffix(".0")
            budgetItemProgress.max = budgetMax.toInt()
            budgetItemProgress.progress = sumOfTransaction.toInt()

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

    private fun viewsColorChange(binding: BudgetItemLayoutBinding, budget: Budget) {
        when (budget.budgetCategory) {
            "Shopping" -> itemColorChange(binding, R.color.yellow_100, R.color.yellow_20)
            "Food" -> itemColorChange(binding, R.color.red, R.color.red_20)
            "Salary" -> itemColorChange(binding, R.color.green_100, R.color.green_20)
            "Transportation" -> itemColorChange(binding, R.color.blue_100, R.color.blue_20)
            "Subscription" -> itemColorChange(binding, R.color.violate, R.color.violate_20)
        }
    }

    private fun itemColorChange(binding: BudgetItemLayoutBinding, darkColor: Int, lightColor: Int) {
        binding.budgetItemView.backgroundTintList = ColorStateList
            .valueOf(ContextCompat.getColor(context, darkColor))
        binding.budgetItemProgress.trackColor = ContextCompat.getColor(context, lightColor)
        binding.budgetItemProgress.setIndicatorColor(
            ContextCompat.getColor(context, darkColor)
        )
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