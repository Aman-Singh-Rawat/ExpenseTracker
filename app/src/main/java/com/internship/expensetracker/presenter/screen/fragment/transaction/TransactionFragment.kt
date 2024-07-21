package com.internship.expensetracker.presenter.screen.fragment.transaction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.room.PrimaryKey
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.MonthsAdapter
import com.internship.expensetracker.data.models.RecentTransItem
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.data.models.TransactionGroup
import com.internship.expensetracker.databinding.FragmentTransactionBinding
import com.internship.expensetracker.databinding.MonthsItemBinding
import com.internship.expensetracker.databinding.MonthsListItemBinding
import com.internship.expensetracker.presenter.adapters.ParentTransactionAdapter
import com.internship.expensetracker.presenter.adapters.RecentTransAdapter
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory
import com.internship.expensetracker.util.Constant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TransactionFragment : Fragment(), RecentTransAdapter.onBudgetItemClicked {
    private val parentTransactionAdapter by lazy {
        ParentTransactionAdapter(requireActivity(), this)
    }
    val adapter = MonthsAdapter(Constant.monthsName())
    private lateinit var binding: FragmentTransactionBinding
    private val viewModel: TransactionViewModel by activityViewModels {
        TransactionViewModelFactory(
            ExpenseRepository(
                TransactionDatabase.getDatabaseInstance(requireActivity()).transactionDao()
            )
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.statusBarColor = resources.getColor(android.R.color.transparent, null)
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    private fun setUpViews() {
        applyRecentRecycler()

        binding.imgFilter.setOnClickListener {
            findNavController().navigate(R.id.filterTransactionBottomSheet)
        }

        binding.tvTranMonth.setOnClickListener {
            val dialogBinding = MonthsListItemBinding.inflate(LayoutInflater.from(requireContext()))

            val monthDialog = AlertDialog.Builder(requireContext()).create()
            monthDialog.setView(dialogBinding.root)
            dialogBinding.rvMonthsItem.adapter = adapter
            monthDialog.show()

        }
    }

    private fun applyRecentRecycler() {
        binding.rvTodayTrans.adapter = parentTransactionAdapter

        viewModel.transactionList.observe(viewLifecycleOwner) { transactions ->
            parentTransactionAdapter.updateUi(transactions)
        }

    }

    override fun onBudgetClicked(transactionId: String) {
        findNavController().navigate(R.id.detailTransaction,
            bundleOf(Constant.TRANSACTION_ID to transactionId))
    }
}