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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.UserAddExpense
import com.internship.expensetracker.databinding.FragmentExpenseAddBinding
import com.internship.expensetracker.presenter.adapters.CustomSpinnerAdapter
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity
import com.internship.expensetracker.presenter.viewmodel.ExpenseAddViewModel
import com.internship.expensetracker.util.base64ToBitmap

class ExpenseAddFragment : Fragment() {
    private lateinit var binding: FragmentExpenseAddBinding
    private val expenseViewModel: ExpenseAddViewModel by activityViewModels()
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

        binding.btnSignUp.setOnClickListener {
            val category = binding.spinnerCategory.selectedItem.toString()
            val description = binding.etDescription.text.toString()
            val wallet = binding.spinnerWallet.selectedItem.toString()

            expenseViewModel.addAllExpenseData(category, description, wallet)
        }

        expenseViewModel.expenseLiveData.observe(requireActivity(), Observer {
            imageSetOrNot(it)
        })

        val spinnerItems = listOf("Aman", "Rajveer", "Tanuj", "Vipul")
        val adapter = CustomSpinnerAdapter(requireContext(), spinnerItems)
        binding.spinnerCategory.adapter = adapter
        binding.spinnerWallet.adapter = adapter

        binding.llAddAttachment.setOnClickListener { findNavController()
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

    override fun onDestroyView() {
        super.onDestroyView()

        expenseViewModel.destroyExpense()
    }
}