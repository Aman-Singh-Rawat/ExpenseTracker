package com.internship.expensetracker.presenter.screen.fragment.budget

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.FragmentBudgetBinding
import com.internship.expensetracker.presenter.adapters.BudgetAdapter
import com.internship.expensetracker.presenter.adapters.RecentTransAdapter
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.BudgetRepository
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.viewmodel.BudgetViewModel
import com.internship.expensetracker.presenter.viewmodel.BudgetViewModelProvider
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory
import com.internship.expensetracker.util.Constant
import java.text.SimpleDateFormat
import java.util.Calendar

class BudgetFragment : Fragment(), RecentTransAdapter.BudgetItemClicked {
    private var transactionList: List<Transaction> = listOf()
    private val calendar = Calendar.getInstance()
    private val simpleDateFormat = SimpleDateFormat("MMMM")
    private val budgetAdapter by lazy {
        BudgetAdapter(requireActivity(), this)
    }
    private lateinit var binding: FragmentBudgetBinding
    private val viewModel: BudgetViewModel by activityViewModels {
        BudgetViewModelProvider(
            BudgetRepository(
                TransactionDatabase.getDatabaseInstance(requireActivity().applicationContext).budgetDao()
            )
        )
    }

    private val transactionViewModel: TransactionViewModel by activityViewModels {
        TransactionViewModelFactory(
            ExpenseRepository(
                TransactionDatabase.getDatabaseInstance(requireActivity().applicationContext).transactionDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.BudgetTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        binding = FragmentBudgetBinding.inflate(localInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.violate, null)
        setUpUi()
    }

    private fun setUpUi() {
        initializeCalendar()
        binding.rvBudget.adapter = budgetAdapter
        binding.fabCreateBtn.setOnClickListener { findNavController().navigate(R.id.createBudgetFragment) }
        setUpMonth()
        updateBudgetData()
    }

    private fun initializeCalendar() {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    private fun setUpMonth() {
        updateMonthDisplay()
        binding.imgPrevious.setOnClickListener { changeMonth(-1) }
        binding.imgNext.setOnClickListener { changeMonth(1) }
    }

    private fun changeMonth(delta: Int) {
        calendar.add(Calendar.MONTH, delta)
        updateMonthDisplay()
        updateBudgetData()
    }

    private fun updateMonthDisplay() {
        binding.tvMonth.text = simpleDateFormat.format(calendar.time)
    }

    private fun updateBudgetData() {
        transactionViewModel.transactionList.observe(viewLifecycleOwner) {
            transactionList = it
        }

        val endDate = getEndOfMonth(calendar)
        viewModel.getSelectedBudget(calendar.time, endDate.time)
            .observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    binding.tvBudgetEmpty.visibility = View.VISIBLE
                    binding.rvBudget.visibility = View.GONE
                } else {
                    binding.tvBudgetEmpty.visibility = View.GONE
                    binding.rvBudget.visibility = View.VISIBLE
                    budgetAdapter.updateUi(it, matchingCategory())
                }
            }
    }

    private fun matchingCategory(): Map<String, Transaction> {
        return transactionList
            .groupBy { it.category }
            .mapValues { (category, transactions) ->
                val sum = transactions
                    .filter { it.transactionType == Constant.EXPENSE }
                    .sumOf { it.transactionMoney }
                Transaction(category = category, transactionMoney = sum, transactionType = Constant.EXPENSE)
            }
    }

    private fun getEndOfMonth(cal: Calendar): Calendar {
        return (cal.clone() as Calendar).apply {
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        } as Calendar
    }

    override fun onBudgetClicked(transactionId: String) {
        findNavController().navigate(R.id.detailBudgetFragment,
            bundleOf(Constant.TRANSACTION_ID to transactionId))
    }
}