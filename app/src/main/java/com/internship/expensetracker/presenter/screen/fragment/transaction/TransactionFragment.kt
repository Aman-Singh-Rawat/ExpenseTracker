package com.internship.expensetracker.presenter.screen.fragment.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.RecentTransItem
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.data.models.TransactionGroup
import com.internship.expensetracker.databinding.FragmentTransactionBinding
import com.internship.expensetracker.presenter.adapters.ParentTransactionAdapter
import com.internship.expensetracker.presenter.adapters.RecentTransAdapter
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TransactionFragment : Fragment(), RecentTransAdapter.onBudgetItemClicked {
    private val parentTransactionAdapter by lazy {
        ParentTransactionAdapter(requireActivity(), this)
    }
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
        activity?.window?.statusBarColor = resources.getColor(R.color.white, null)
        super.onViewCreated(view, savedInstanceState)

        applyRecentRecycler()

        binding.imgFilter.setOnClickListener {
            findNavController().navigate(R.id.filterTransactionBottomSheet)
        }
    }

    private fun applyRecentRecycler() {
        binding.rvTodayTrans.adapter = parentTransactionAdapter
        viewModel.transactionList.observe(viewLifecycleOwner) {

        }
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

    override fun onBudgetClicked(transactionUi: String) {
        TODO("Not yet implemented")
    }
}