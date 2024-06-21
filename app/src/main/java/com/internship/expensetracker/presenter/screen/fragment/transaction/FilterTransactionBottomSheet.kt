package com.internship.expensetracker.presenter.screen.fragment.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentFilterTransactionBottomSheetBinding
import com.internship.expensetracker.presenter.adapters.FilterByAdapter

class FilterTransactionBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterTransactionBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterTransactionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFilterBy.adapter = FilterByAdapter(listOf("Income", "Expense", "Transfer"))

        binding.rvSortBy.adapter = FilterByAdapter(listOf("Highest", "Lowest", "Newest", "Oldest"))
    }
}