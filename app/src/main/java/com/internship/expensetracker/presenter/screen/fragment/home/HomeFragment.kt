package com.internship.expensetracker.presenter.screen.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.core.BaseFragment
import com.internship.expensetracker.data.models.RecentTransItem
import com.internship.expensetracker.databinding.FragmentHomeBinding
import com.internship.expensetracker.presenter.adapters.RecentTransAdapter
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory

class HomeFragment : BaseFragment() {
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(
            ExpenseRepository(
                TransactionDatabase.getDatabaseInstance(requireActivity().applicationContext)
                    .transactionDao()
            )
        )
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val recentTransAdapter = RecentTransAdapter()
    private var isSelectedTime: String = "Today"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setUpRecycler()
        setupListeners()
    }

    private fun setUpRecycler() {
        binding.rvRecentTrans.adapter = recentTransAdapter
        transactionViewModel.transactionList.observe(viewLifecycleOwner, Observer {
            recentTransAdapter.updateUi(it)
        })
    }

    private fun setupUI() {
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.yellow_20)
    }

    private fun setupListeners() {
        binding.llExpense.setOnClickListener {
            findNavController().navigate(R.id.detailTransaction)
        }

        binding.tvTagToday.setOnClickListener { selectTag("Today", it) }
        binding.tvTagWeek.setOnClickListener { selectTag("Week", it) }
        binding.tvTagMonth.setOnClickListener { selectTag("Month", it) }
        binding.tvTagYear.setOnClickListener { selectTag("Year", it) }
    }

    private fun selectTag(tag: String, view: View) {
        resetTags()
        isSelectedTime = tag
        view.setBackgroundResource(R.drawable.bg_weeks)
        (view as TextView).setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.yellow_100
            )
        )
    }

    private fun resetTags() {
        listOf(
            binding.tvTagToday,
            binding.tvTagWeek,
            binding.tvTagMonth,
            binding.tvTagYear
        ).forEach { tv ->
            tv.background = null
            tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
    }

    private fun transList(): List<RecentTransItem> {
        // This should ideally come from a ViewModel
        return listOf(
            RecentTransItem(
                R.drawable.ic_yellow_trash,
                "Shopping",
                "Buy Some grocery",
                120,
                "10:00 AM"
            ),
            RecentTransItem(
                R.drawable.ic_subscription,
                "Subscription",
                "Disney+ Annual",
                80,
                "03:30 PM"
            ),
            RecentTransItem(R.drawable.ic_food, "Food", "Buy a ramen", 32, "07:30 PM")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}