package com.internship.expensetracker.presenter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.data.models.TransactionGroup
import com.internship.expensetracker.databinding.RecentTransacationItemBinding
import com.internship.expensetracker.databinding.TransactionItemBinding
import com.internship.expensetracker.presenter.adapters.RecentTransAdapter.onBudgetItemClicked
import com.internship.expensetracker.presenter.screen.fragment.transaction.TransactionFragment
import com.internship.expensetracker.util.Constant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ParentTransactionAdapter(private val context: Context, private val listener: onBudgetItemClicked): RecyclerView.Adapter<ParentTransactionAdapter.ParentTransactionViewHolder>() {
    private var transactionList: List<TransactionGroup> = emptyList()
    private val recentTransAdapter = RecentTransAdapter(context, listener)
    inner class ParentTransactionViewHolder(val binding: TransactionItemBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentTransactionViewHolder {
        val binding = TransactionItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ParentTransactionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: ParentTransactionViewHolder, position: Int) {
        holder.binding.tvDate.text = dateFunctionality(transactionList[position].date)
        holder.binding.rvChildTransaction.adapter = recentTransAdapter
        recentTransAdapter.updateUi(transactionList[position].transactions)
    }

    fun updateUi(transactionList: List<TransactionGroup>) {
        this.transactionList = transactionList
        notifyDataSetChanged()
    }

    private fun dateFunctionality(date: Date): String {
        val todayDate = Calendar.getInstance().apply { setToMidnight() }
        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, -1)
            setToMidnight()
        }


        if (date > todayDate.time) {
            return "Today"
        } else if (date > yesterday.time){
            return "Yesterday"
        } else {
            val sdf = SimpleDateFormat("MMMM dd, yyy", Locale.getDefault())
            return sdf.format(date)
        }
    }
    private fun Calendar.setToMidnight() {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

}