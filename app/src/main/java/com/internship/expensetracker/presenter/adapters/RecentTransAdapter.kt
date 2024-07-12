package com.internship.expensetracker.presenter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.RecentTransacationItemBinding
import com.internship.expensetracker.util.Constant
import java.text.SimpleDateFormat
import java.util.Locale

class RecentTransAdapter(private val context: Context, private val listener: onBudgetItemClicked): RecyclerView.Adapter<RecentTransAdapter.RecentTransViewHolder>() {
    var recentList: List<Transaction> = emptyList()
    inner class RecentTransViewHolder(val binding: RecentTransacationItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(transaction: Transaction) {
                binding.apply {
                    //imgTranImage.setImageResource(recentList[position].transImage)
                    tvTranType.text = transaction.category
                    tvTranDescription.text = transaction.description

                    val timeFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
                    tvTranTime.text = timeFormat.format(transaction.transactionTime)
                    //tvTranTime.text = recentList[position].transactionTime.time.to

                    if (transaction.transactionType == Constant.EXPENSE) {
                        tvTranTypeMoney.setTextColor(context.getColor(R.color.red))
                    } else {
                        tvTranTypeMoney.setTextColor(context.getColor(R.color.green))
                    }
                    tvTranTypeMoney.text = transaction.transactionMoney
                }
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

    interface onBudgetItemClicked {
        fun onBudgetClicked(transactionUi: String)
    }
}