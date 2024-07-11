package com.internship.expensetracker.presenter.screen.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import com.internship.expensetracker.util.Constant
import com.internship.expensetracker.util.base64ToBitmap
import java.util.Date

class ExpenseAddFragment : BaseFragment() {
    private val transactionType: String by lazy { arguments?.getString(Constant.TRANSACTION_TYPE) ?: "" }
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

        setUpViews()
    }

    private fun setUpViews() {
        if (transactionType == Constant.EXPENSE) {
            activity?.window?.statusBarColor = resources.getColor(R.color.red, null)
            binding.root.setBackgroundColor(requireContext().getColor(R.color.red))
        } else {
            activity?.window?.statusBarColor = resources.getColor(R.color.green, null)
            binding.root.setBackgroundColor(requireContext().getColor(R.color.green))
        }
        binding.ivExpenseBackPressed.setOnClickListener {
            (activity as HomeContainerActivity).onBackPressed()
        }

        binding.imgClearImage.setOnClickListener {
            clearImage()
        }

        binding.btnContinue.setOnClickListener {
            sendUserData()
        }
        //val adapter = CustomSpinnerAdapter(requireContext(), spinnerItems)

        val spinnerItems = arrayOf("Shopping", "Subscription", "Food", "Salary", "Transportation")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            spinnerItems
        )
        binding.spinnerCategory.setAdapter(adapter)
        binding.spinnerWallet.setAdapter(adapter)

        binding.llAddAttachment.setOnClickListener {
            findNavController()
                .navigate(R.id.expenseAddBottomSheetFragment)
        }
                parentFragmentManager.setFragmentResultListener("dataFromSecond",
                    viewLifecycleOwner) {_, bundle ->

                    val result = bundle.getString("data")
                    result?.let {
                        binding.rlExpenseFile.visibility = View.VISIBLE
                        binding.imgUserExpense.setImageURI(result.toUri())
                        binding.llAddAttachment.visibility = View.GONE
                        Log.d("debugging", result)
                    }
                }
    }

    private fun sendUserData() {
        val balance = binding.etBudgetMoney.text.toString()
        val category = binding.spinnerCategory.text.toString()
        val description = binding.etDescription.text.toString()
        val wallet = binding.spinnerWallet.text.toString()

        val string: String = Date().time.toString()

        Log.d("debugging", category)
        if (isDetailsFill(balance, category, description, wallet)) {

            if (transactionType == Constant.EXPENSE) {
                transactionViewModel.insertTransaction(
                    Transaction(
                        0, transactionMoney = ((balance.toInt() * -1).toString()), category = category,
                        description = description, wallet = wallet, transactionType = transactionType
                    )
                )
            } else {
                transactionViewModel.insertTransaction(
                    Transaction(
                        0, transactionMoney = balance, category = category,
                        description = description, wallet = wallet, transactionType = transactionType
                    )
                )
            }
            findNavController().navigateUp()
        }
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

    private fun isDetailsFill(balance: String, category: String, description: String, wallet: String): Boolean {
        return when {
            balance.isEmpty() -> {
                showToast("Balance not to be empty")
                false
            }
            category == "Category" -> {
                showToast("Please Select Category")
                false
            }
            description.isEmpty() -> {
                showToast("Description not be empty")
                false
            }
            wallet == "Wallet" -> {
                showToast("Please Select Wallet")
                false
            }
            else -> true
        }
    }
}