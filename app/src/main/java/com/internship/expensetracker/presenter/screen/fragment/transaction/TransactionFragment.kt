package com.internship.expensetracker.presenter.screen.fragment.transaction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.RecentTransItem
import com.internship.expensetracker.databinding.ActivityHomeContainerBinding
import com.internship.expensetracker.databinding.FragmentTransactionBinding
import com.internship.expensetracker.presenter.adapters.RecentTransAdapter
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity

class TransactionFragment : Fragment() {
    private val recentTransAdapter = RecentTransAdapter()
    private lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.statusBarColor = resources.getColor(R.color.white, null)
        super.onViewCreated(view, savedInstanceState)

        applyRecentRecycler()

        binding.imgFilter.setOnClickListener {
            findNavController().navigate(R.id.filterTransactionBottomSheet)
        }
    }

    private fun applyRecentRecycler() {
        binding.rvTodayTrans.adapter = recentTransAdapter
        binding.rvYesterdayTrans.adapter = recentTransAdapter
        //recentTransAdapter.updateUi(recentList = transList())
    }

    private fun transList(): List<RecentTransItem> {
        return listOf(
            RecentTransItem(R.drawable.ic_yellow_trash, "Shopping",
            "Buy Some grocery", 120, "10:00 AM"),
            RecentTransItem(R.drawable.ic_subscription, "Subscription",
                "Disney+ Annual", 80, "03:30 PM"),
            RecentTransItem(R.drawable.ic_food, "Food", "Buy a ramen",
                32, "07:30 PM")
        )
    }
}