package com.internship.expensetracker.presenter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.databinding.TransactionItemBinding
import com.internship.expensetracker.data.models.Transaction
import java.text.SimpleDateFormat
import java.util.*

class ParentTransactionAdapter(
    private val context: Context,
    private val listener: RecentTransAdapter.onBudgetItemClicked
) : RecyclerView.Adapter<ParentTransactionAdapter.ParentTransactionViewHolder>() {

    private var headingGroups: Map<String, List<Transaction>> = emptyMap()

    inner class ParentTransactionViewHolder(val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentTransactionViewHolder {
        val binding = TransactionItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentTransactionViewHolder(binding)
    }

    override fun getItemCount(): Int = headingGroups.size

    override fun onBindViewHolder(holder: ParentTransactionViewHolder, position: Int) {
        val (heading, transactions) = headingGroups.entries.elementAt(position)
        holder.binding.tvDate.text = heading  // Using tvDate for heading

        val childAdapter = RecentTransAdapter(context, listener)
        holder.binding.rvChildTransaction.adapter = childAdapter
        childAdapter.updateUi(transactions)
    }

    fun updateUi(transactions: List<Transaction>) {
        this.headingGroups = groupTransactionsByHeading(transactions)
        notifyDataSetChanged()
    }

    private fun groupTransactionsByHeading(transactions: List<Transaction>): Map<String, List<Transaction>> {
        return transactions.groupBy { getHeading(it.transactionTime) }
            .toSortedMap(compareByDescending { parseHeading(it) })
    }

    private fun getHeading(date: Date): String {
        val todayDate = Calendar.getInstance().apply { setToMidnight() }
        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, -1)
            setToMidnight()
        }

        return when {
            date >= todayDate.time -> "Today"
            date >= yesterday.time -> "Yesterday"
            else -> SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
        }
    }

    private fun parseHeading(heading: String): Date {
        return when (heading) {
            "Today" -> Calendar.getInstance().time
            "Yesterday" -> Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -1) }.time
            else -> SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).parse(heading) ?: Date(0)
        }
    }

    private fun Calendar.setToMidnight() {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
}