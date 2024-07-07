package com.internship.expensetracker.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.data.models.RecentTransItem
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.RecentTransacationItemBinding

class RecentTransAdapter: RecyclerView.Adapter<RecentTransAdapter.RecentTransViewHolder>() {
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
            tvTranTypeMoney.text = recentList[position].transactionMoney.toString()
            tvTranDescription.text = recentList[position].description
            //tvTranTime.text = recentList[position].transactionTime.time.to
        }
    }

    fun updateUi(recentList: List<Transaction>) {
        this.recentList = recentList
        notifyDataSetChanged()
    }
}