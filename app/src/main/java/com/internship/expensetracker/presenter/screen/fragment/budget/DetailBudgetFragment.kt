package com.internship.expensetracker.presenter.screen.fragment.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.Budget
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.FragmentDetailBudgetBinding
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.BudgetRepository
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity
import com.internship.expensetracker.presenter.viewmodel.BudgetViewModel
import com.internship.expensetracker.presenter.viewmodel.BudgetViewModelProvider
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory
import com.internship.expensetracker.util.Constant

class DetailBudgetFragment : Fragment() {
    private val transactionId by lazy { arguments?.getString(Constant.TRANSACTION_ID) ?: "" }
    private lateinit var binding: FragmentDetailBudgetBinding
    private var transactionList: List<Transaction> = listOf()
    private var budget = Budget()

    private val budgetViewModel: BudgetViewModel by viewModels {
        BudgetViewModelProvider(
            BudgetRepository(
                TransactionDatabase.getDatabaseInstance(requireActivity().applicationContext)
                    .budgetDao()
            )
        )
    }
    private val transactionViewMode: TransactionViewModel by viewModels {
        TransactionViewModelFactory(
            ExpenseRepository(
                TransactionDatabase.getDatabaseInstance(requireActivity().applicationContext)
                    .transactionDao()
            )
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.statusBarColor = resources.getColor(R.color.white, null)
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    private fun setUpViews() {
        binding.icFragDetailArrow.setOnClickListener {
            (activity as HomeContainerActivity).onBackPressed()
        }
        budgetViewModel.getBudget(transactionId)
        budgetViewModel.budget.observe(viewLifecycleOwner) {
            budget = it
            transactionViewMode.getTransactionUsingQuery(it.budgetCategory)
                .observe(viewLifecycleOwner) { transactions ->
                    println(transactions)
                    val sumOfMoney = transactions.sumOf { it.transactionMoney }
                    setTheValueOnViews(sumOfMoney)
                }
        }

        binding.imgTrash.setOnClickListener {
            findNavController().navigate(R.id.deleteBudgetBottomSheetFragment,
                bundleOf(Constant.TRANSACTION_ID to transactionId))
        }
    }

    private fun setTheValueOnViews(sumOfMoney: Double) {
        val remaining = (budget.budgetMoney - (sumOfMoney * -1))
        binding.tvBudgetCategory.text = budget.budgetCategory
        binding.budgetItemProgress.max = budget.budgetMoney.toInt()
        binding.budgetItemProgress.progress = (sumOfMoney * -1).toInt()

        if (remaining < 0) {
            binding.tvBudgetMoney.text = "$0"
            binding.tvExceedLimit.visibility = View.VISIBLE
        } else {
            binding.tvBudgetMoney.text = remaining.toString().removeSuffix(".0")
            binding.tvExceedLimit.visibility = View.GONE
        }


    }

}