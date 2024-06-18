package com.internship.expensetracker.presenter.screen.fragment.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentExpenseAddBinding
import com.internship.expensetracker.presenter.adapters.CustomSpinnerAdapter
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity

class ExpenseAddFragment : Fragment() {
    private lateinit var binding: FragmentExpenseAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val spinnerItems = listOf("Aman", "Rajveer", "Tanuj", "Vipul")
        val adapter = CustomSpinnerAdapter(requireContext(), spinnerItems)
        binding.spinnerCategory.adapter = adapter
        binding.spinnerWallet.adapter = adapter

        binding.llAddAttachment.setOnClickListener { findNavController()
            .navigate(R.id.expenseAddBottomSheetFragment) }
    }
}