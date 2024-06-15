package com.internship.expensetracker.presenter.screen.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.core.BaseFragment
import com.internship.expensetracker.data.models.RecentTransItem
import com.internship.expensetracker.databinding.FragmentHomeBinding
import com.internship.expensetracker.presenter.adapters.RecentTransAdapter

class HomeFragment : BaseFragment() {
    private var isSelectedTime: String = "Today"
    private lateinit var binding: FragmentHomeBinding
    private val recentTransAdapter = RecentTransAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        userTagClickHandle()
        applyRecentRecycler()
        return binding.root
    }

    private fun applyRecentRecycler() {
        binding.rvRecentTrans.adapter = recentTransAdapter
        recentTransAdapter.updateUi(recentList = transList())
    }

    private fun userTagClickHandle() {
        binding.apply {
            tvTagToday.setOnClickListener {
                resetTags()
                tvTagToday.setBackgroundResource(R.drawable.bg_weeks)
                tvTagToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_100))
                isSelectedTime = "Today"
            }
            tvTagWeek.setOnClickListener {
                resetTags()
                tvTagWeek.setBackgroundResource(R.drawable.bg_weeks)
                tvTagWeek.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_100))
                isSelectedTime = "Week"
            }
            tvTagMonth.setOnClickListener {
                resetTags()
                tvTagMonth.setBackgroundResource(R.drawable.bg_weeks)
                tvTagMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_100))
                isSelectedTime = "Month"
            }
            tvTagYear.setOnClickListener {
                resetTags()
                tvTagYear.setBackgroundResource(R.drawable.bg_weeks)
                tvTagYear.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_100))
                isSelectedTime = "Year"
            }
        }
    }

    private fun resetTags() {
        binding.apply {
            tvTagToday.background = null
            tvTagToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            tvTagWeek.background = null
            tvTagWeek.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            tvTagMonth.background = null
            tvTagMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            tvTagYear.background = null
            tvTagYear.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
    }

    private fun transList(): List<RecentTransItem> {
        return listOf(RecentTransItem(R.drawable.ic_shopping_bag, "Shopping",
            "Buy Some grocery", 120, "10:00 AM"),
            RecentTransItem(R.drawable.ic_subscription, "Subscription",
                "Disney+ Annual", 80, "03:30 PM"),
            RecentTransItem(R.drawable.ic_food, "Food", "Buy a ramen",
                32, "07:30 PM"))
    }

    fun navigateAddFragment() {
        findNavController().navigate(R.id.expenseAddFragment)
    }
}