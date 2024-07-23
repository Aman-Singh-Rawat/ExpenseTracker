package com.internship.expensetracker.presenter.screen.fragment.detail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentRemoveTransactionBottomSheetBinding
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModel
import com.internship.expensetracker.presenter.viewmodel.TransactionViewModelFactory
import com.internship.expensetracker.util.Constant

class RemoveTransactionBottomSheetFragment : BottomSheetDialogFragment() {
    private val transactionId by lazy {
        arguments?.getString(Constant.TRANSACTION_ID) ?: ""
    }
    private lateinit var binding: FragmentRemoveTransactionBottomSheetBinding
    private val viewModel: TransactionViewModel by activityViewModels {
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
        binding = FragmentRemoveTransactionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnYes.setOnClickListener {
            viewModel.deleteTransactionUsingId(transactionId)
            dismiss()
            if (isAdded) {
                val dialog = Dialog(requireActivity())
                (activity as HomeContainerActivity).onBackPressed()
                dialog.setCancelable(false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.custom_toast_removed_successfully)
                dialog.window?.apply {
                    setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

                dialog.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    dialog.dismiss()
                }, 1000)
            }
        }
    }
}