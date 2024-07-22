package com.internship.expensetracker.presenter.screen.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.internship.expensetracker.R
import com.internship.expensetracker.core.BaseFragment
import com.internship.expensetracker.data.models.RecentTransItem
import com.internship.expensetracker.databinding.FragmentHomeBinding
import com.internship.expensetracker.presenter.adapters.RecentTransAdapter
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory
import com.internship.expensetracker.util.Constant
import java.text.SimpleDateFormat
import java.util.Calendar

class HomeFragment : BaseFragment(), RecentTransAdapter.BudgetItemClicked {
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(
            ExpenseRepository(
                TransactionDatabase.getDatabaseInstance(requireActivity().applicationContext)
                    .transactionDao()
            )
        )
    }
    private val currentDate = Calendar.getInstance().time
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val recentTransAdapter by lazy {
        RecentTransAdapter(requireActivity(), this)
    }
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
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.HOUR_OF_DAY, 0)
        startDate.set(Calendar.MINUTE, 0)
        startDate.set(Calendar.SECOND, 0)
        startDate.set(Calendar.MILLISECOND, 0)

        transactionViewModel.getTodayTransaction(
            startDate.time,
            Calendar.getInstance().time
        ).observe(viewLifecycleOwner, Observer { todayTransaction ->
            recentTransAdapter.updateUi(todayTransaction)
        })
    }

    private fun setupUI() {
        val simpleDateFormat = SimpleDateFormat("MMMM")
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.yellow_20)

        binding.tvMonth.text = simpleDateFormat.format(currentDate)
        setTransactionTime()
        transactionViewModel.sumOfTransaction.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                binding.tvAccountBalance.text = "$0"
            } else {
                if (it <= 0) {
                    binding.tvAccountBalance.text = "$0"
                } else {
                    binding.tvAccountBalance.text = "$$it".removeSuffix(".0")
                }
            }
        })

        transactionViewModel.sumOfIncome.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                binding.tvAccountBalance.text = "$0"
            } else {
                binding.tvIncome.text = "$$it".removeSuffix(".0")
            }
        })

        transactionViewModel.sumOfExpense.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                binding.tvAccountBalance.text = "$0"
            } else {
                binding.tvExpense.text = "$" + ("$it".removePrefix("-")).removeSuffix(".0")
            }
        })

    }

    private fun setTransactionTime() {
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.text?.let {
                    if (it == "Today") {
                        val startDate = Calendar.getInstance()
                        startDate.set(Calendar.HOUR_OF_DAY, 0)
                        startDate.set(Calendar.MINUTE, 0)
                        startDate.set(Calendar.SECOND, 0)
                        startDate.set(Calendar.MILLISECOND, 0)

                        transactionViewModel.getTodayTransaction(
                            startDate.time,
                            Calendar.getInstance().time
                        ).observe(viewLifecycleOwner, Observer { todayTransaction ->
                            recentTransAdapter.updateUi(todayTransaction)
                        })
                    } else if (it == "Week") {
                        val startDate = Calendar.getInstance()
                        startDate.set(Calendar.DAY_OF_WEEK, startDate.firstDayOfWeek)
                        startDate.set(Calendar.HOUR_OF_DAY, 0)
                        startDate.set(Calendar.MINUTE, 0)
                        startDate.set(Calendar.SECOND, 0)
                        startDate.set(Calendar.MILLISECOND, 0)

                        transactionViewModel.getTodayTransaction(
                            startDate.time,
                            currentDate
                        ).observe(viewLifecycleOwner, Observer { todayTransaction ->
                            recentTransAdapter.updateUi(todayTransaction)
                        })
                    } else if (it == "Month") {
                        val startDate = Calendar.getInstance()
                        startDate.set(Calendar.DATE, 1)
                        startDate.set(Calendar.HOUR_OF_DAY, 0)
                        startDate.set(Calendar.MINUTE, 0)
                        startDate.set(Calendar.SECOND, 0)
                        startDate.set(Calendar.MILLISECOND, 0)

                        transactionViewModel.getTodayTransaction(
                            startDate.time,
                            currentDate
                        ).observe(viewLifecycleOwner, Observer { todayTransaction ->
                            recentTransAdapter.updateUi(todayTransaction)
                        })
                    } else if (it == "Year") {
                        val startDate = Calendar.getInstance()
                        startDate.set(Calendar.MONTH, Calendar.JANUARY)
                        startDate.set(Calendar.DATE, 1)
                        startDate.set(Calendar.HOUR_OF_DAY, 0)
                        startDate.set(Calendar.MINUTE, 0)
                        startDate.set(Calendar.SECOND, 0)
                        startDate.set(Calendar.MILLISECOND, 0)

                        transactionViewModel.getTodayTransaction(
                            startDate.time,
                            currentDate
                        ).observe(viewLifecycleOwner, Observer { todayTransaction ->
                            recentTransAdapter.updateUi(todayTransaction)
                        })
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("debugging", "onTabUnselected:: ${tab?.text}")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("debugging", "onTabReselected:: ${tab?.text}")
            }

        })
    }

    private fun setupListeners() {
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.HOUR_OF_DAY, 0)
        startDate.set(Calendar.MINUTE, 0)
        startDate.set(Calendar.SECOND, 0)
        startDate.set(Calendar.MILLISECOND, 0)

//        binding.tvTagToday.setOnClickListener {
//            selectTag("Today", it)
//            transactionViewModel.getTodayTransaction(
//                startDate.time, Calendar.getInstance().time
//            )
//                .observe(requireActivity(), Observer { todayTransaction ->
//                    recentTransAdapter.updateUi(todayTransaction)
//                })
//        }
//        binding.tvTagWeek.setOnClickListener { selectTag("Week", it) }
//        binding.tvTagMonth.setOnClickListener { selectTag("Month", it) }
//        binding.tvTagYear.setOnClickListener { selectTag("Year", it) }
    }

    private fun selectTag(tag: String, view: View) {
        //resetTags()
        isSelectedTime = tag
        view.setBackgroundResource(R.drawable.bg_weeks)
        (view as TextView).setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.yellow_100
            )
        )
    }

//    private fun resetTags() {
//        listOf(
//            binding.tvTagToday,
//            binding.tvTagWeek,
//            binding.tvTagMonth,
//            binding.tvTagYear
//        ).forEach { tv ->
//            tv.background = null
//            tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
//        }
//    }

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

    override fun onBudgetClicked(transactionId: String) {
        Log.d("debugging", transactionId)
        findNavController().navigate(
            R.id.detailTransaction,
            bundleOf(Constant.TRANSACTION_ID to transactionId)
        )
    }
}