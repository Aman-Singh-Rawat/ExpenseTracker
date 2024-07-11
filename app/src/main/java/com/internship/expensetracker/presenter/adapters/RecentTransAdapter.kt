package com.internship.expensetracker.presenter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.RecentTransItem
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.RecentTransacationItemBinding
import com.internship.expensetracker.util.Constant
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class RecentTransAdapter(private val context: Context): RecyclerView.Adapter<RecentTransAdapter.RecentTransViewHolder>() {
    var recentList: List<Transaction> = emptyList()
    class RecentTransViewHolder(val binding: RecentTransacationItemBinding)
        : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTransViewHolder {
        val binding = RecentTransacationItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return RecentTransViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  recentList.size
    }

    override fun onBindViewHolder(holder: RecentTransViewHolder, position: Int) {
        holder.binding.apply {
            //imgTranImage.setImageResource(recentList[position].transImage)
            tvTranType.text = recentList[position].category
            tvTranDescription.text = recentList[position].description

            val timeFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
            tvTranTime.text = timeFormat.format(recentList[position].transactionTime)
            //tvTranTime.text = recentList[position].transactionTime.time.to

            if (recentList[position].transactionType == Constant.EXPENSE) {
                tvTranTypeMoney.setTextColor(context.getColor(R.color.red))
            } else {
                tvTranTypeMoney.setTextColor(context.getColor(R.color.green))
            }
            tvTranTypeMoney.text = recentList[position].transactionMoney
        }
    }

    fun updateUi(recentList: List<Transaction>) {
        this.recentList = recentList
        notifyDataSetChanged()
    }
}