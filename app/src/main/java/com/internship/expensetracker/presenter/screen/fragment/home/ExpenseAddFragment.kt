package com.internship.expensetracker.presenter.screen.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.core.BaseFragment
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.data.models.UserAddExpense
import com.internship.expensetracker.databinding.FragmentExpenseAddBinding
import com.internship.expensetracker.presenter.adapters.CustomSpinnerAdapter
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity
import com.internship.expensetracker.presenter.viewmodel.ExpenseAddViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory
import com.internship.expensetracker.util.base64ToBitmap
import java.util.Date

class ExpenseAddFragment : BaseFragment() {
    private lateinit var binding: FragmentExpenseAddBinding
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(ExpenseRepository(
            TransactionDatabase.getDatabaseInstance(requireActivity().applicationContext).transactionDao()
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenseAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.red, null)

        binding.ivExpenseBackPressed.setOnClickListener {
            (activity as HomeContainerActivity).onBackPressed()
        }

        binding.imgClearImage.setOnClickListener {
            clearImage()
        }

        binding.btnSignUp.setOnClickListener {
            sendUserData()
        }

        val spinnerItems = listOf("Aman", "Rajveer", "Tanuj", "Vipul")
        val adapter = CustomSpinnerAdapter(requireContext(), spinnerItems)
        binding.spinnerCategory.adapter = adapter
        binding.spinnerWallet.adapter = adapter

        binding.llAddAttachment.setOnClickListener {
            findNavController()
                .navigate(R.id.expenseAddBottomSheetFragment)
        }
//        parentFragmentManager.setFragmentResultListener("dataFromSecond",
//            viewLifecycleOwner) {_, bundle ->
//
//            val result = bundle.getString("data")
//            result?.let {
//                binding.rlExpenseFile.visibility = View.VISIBLE
//                binding.imgUserExpense.setImageBitmap(base64ToBitmap(result))
//                binding.llAddAttachment.visibility = View.GONE
//                Log.d("debugging", result)
//            }
//        }
    }

    private fun sendUserData() {
        val balance = binding.etBudgetMoney.text.toString()
        val category = binding.spinnerCategory.selectedItem.toString()
        val description = binding.etDescription.text.toString()
        val wallet = binding.spinnerWallet.selectedItem.toString()

        transactionViewModel.insertTransaction(
            Transaction(
                0, balance, category, description,
                wallet, "", ""
            )
        )

        findNavController().navigateUp()
    }

    private fun clearImage() {
        binding.imgUserExpense.setImageURI(null)
        binding.rlExpenseFile.visibility = View.GONE
        binding.llAddAttachment.visibility = View.VISIBLE
    }

    private fun imageSetOrNot(it: UserAddExpense) {
        if (it.expenseAttachment.isNotEmpty()) {
            binding.rlExpenseFile.visibility = View.VISIBLE
            binding.imgUserExpense.setImageURI(it.expenseAttachment.toUri())
            binding.llAddAttachment.visibility = View.GONE
        } else {
            binding.rlExpenseFile.visibility = View.GONE
            binding.imgUserExpense.setImageURI(null)
            binding.llAddAttachment.visibility = View.VISIBLE
        }
    }
}