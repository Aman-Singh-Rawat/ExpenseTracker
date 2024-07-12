package com.internship.expensetracker.presenter.screen.fragment.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.databinding.FragmentDetailTransactionBinding
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory
import com.internship.expensetracker.util.Constant
import java.text.SimpleDateFormat
import java.util.Locale

class DetailTransaction : Fragment() {
    private lateinit var binding: FragmentDetailTransactionBinding
    private var deleteTransaction: Transaction? = null
    private val transactionId by lazy { arguments?.getString(Constant.TRANSACTION_ID) ?: "" }
    private val viewModel: TransactionViewModel by viewModels {
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
        binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSelectedTransaction(transactionId)
            .observe(requireActivity(), Observer { transaction ->
                if (isAdded) {
                    deleteTransaction = transaction
                    if (transaction.transactionType == Constant.EXPENSE) {
                        activity?.window?.statusBarColor = resources.getColor(R.color.red, null)
                        binding.tvMoneyMotive.text = "Buy some ${transaction.category}"
                        binding.llFragDetailTop.backgroundTintList =
                            requireContext().resources.getColorStateList(R.color.red, null)
                    } else if (transaction.transactionType == Constant.INCOME) {
                        val formatter = SimpleDateFormat("MMMM", Locale.ENGLISH)
                        binding.tvMoneyMotive.text =
                            "${transaction.category} for ${formatter.format(transaction.transactionTime)}"

                        activity?.window?.statusBarColor = resources.getColor(R.color.green, null)
                        binding.llFragDetailTop.backgroundTintList =
                            requireContext().resources.getColorStateList(R.color.green, null)
                    }

                    binding.tvTransactionMoney.text = transaction.transactionMoney.removePrefix("-")
                    val formatter = SimpleDateFormat("EEEE d MMMM yyyy    HH:mm", Locale.ENGLISH)
                    binding.tvDate.text = formatter.format(transaction.transactionTime)
                    binding.tvTransactionType.text = transaction.transactionType
                    binding.tvTransactionCategory.text = transaction.category
                    binding.tvTransactionWallet.text = transaction.wallet
                    binding.tvDescription.text = transaction.description
                }
            })

        binding.icFragDetailArrow.setOnClickListener {
            (activity as HomeContainerActivity).onBackPressed()
        }

        binding.imgTrash.setOnClickListener {
            val bottomSheet = RemoveTransactionBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, "bottomSheet")
            findNavController().navigate(R.id.removeTransactionBottomSheetFragment)
        }
    }
}