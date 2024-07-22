package com.internship.expensetracker.presenter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.RecentTransacationItemBinding
import com.internship.expensetracker.util.Constant
import java.text.SimpleDateFormat
import java.util.Locale

class RecentTransAdapter(private val context: Context, private val listener: RecentTransAdapter.BudgetItemClicked): RecyclerView.Adapter<RecentTransAdapter.RecentTransViewHolder>() {
    private var recentList: List<Transaction> = emptyList()
    inner class RecentTransViewHolder(val binding: RecentTransacationItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(transaction: Transaction) {
                binding.apply {
                    changeImage(transaction, binding)
                    //imgTranImage.setImageResource(recentList[position].transImage)
                    tvTranType.text = transaction.category
                    tvTranDescription.text = transaction.description

                    val timeFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
                    tvTranTime.text = timeFormat.format(transaction.transactionTime)
                    //tvTranTime.text = recentList[position].transactionTime.time.to

                    if (transaction.transactionType == Constant.EXPENSE) {
                        tvTranTypeMoney.setTextColor(context.getColor(R.color.red))
                    } else {
                        tvTranTypeMoney.setTextColor(context.getColor(R.color.green_100))
                    }
                    tvTranTypeMoney.text = transaction.transactionMoney.toString()
                }
            }
        }

    private fun changeTransactionImage(binding: RecentTransacationItemBinding, image: Int, lightColor: Int) {
        binding.cvTranImage.setCardBackgroundColor(ContextCompat.getColor(context, lightColor))
        binding.imgTranImage.setImageResource(image)
    }

    private fun changeImage(transaction: Transaction, binding: RecentTransacationItemBinding) {
        when (transaction.category) {
            "Shopping" -> changeTransactionImage(binding, R.drawable.ic_yellow_trash, R.color.yellow_20)
            "Subscription" -> changeTransactionImage(binding,R.drawable.ic_subscription, R.color.violate_20)
            "Food" -> changeTransactionImage(binding,R.drawable.ic_food, R.color.red_20)
            "Salary" -> changeTransactionImage(binding,R.drawable.ic_salary, R.color.green_20)
            "Transportation" -> changeTransactionImage(binding,R.drawable.ic_transportation, R.color.blue_20)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTransViewHolder {
        val binding = RecentTransacationItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return RecentTransViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  recentList.size
    }

    override fun onBindViewHolder(holder: RecentTransViewHolder, position: Int) {
        holder.bind(recentList[position])
        holder.itemView.setOnClickListener {
            listener.onBudgetClicked(recentList[position].transactionId)
        }
    }

    fun updateUi(recentList: List<Transaction>) {
        this.recentList = recentList
        notifyDataSetChanged()
    }

    interface BudgetItemClicked {
        fun onBudgetClicked(transactionId: String)
    }
}