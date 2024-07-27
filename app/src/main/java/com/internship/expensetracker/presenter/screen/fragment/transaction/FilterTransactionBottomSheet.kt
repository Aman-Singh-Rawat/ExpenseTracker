package com.internship.expensetracker.presenter.screen.fragment.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.expensetracker.databinding.FragmentFilterTransactionBottomSheetBinding
import com.internship.expensetracker.presenter.adapters.FilterByAdapter

class FilterTransactionBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterTransactionBottomSheetBinding
    private val filterAdapter = FilterByAdapter()
    private val sortAdapter = FilterByAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterTransactionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    private fun setUpViews() {
        binding.rvFilterBy.adapter = filterAdapter
        filterAdapter.updateUi(listOf("Income", "Expense", "Transfer"))

        binding.rvSortBy.adapter = sortAdapter
        sortAdapter.updateUi(listOf("Highest", "Lowest", "Newest", "Oldest"))

        binding.tvReset.setOnClickListener {
            filterAdapter.resetList()
            sortAdapter.resetList()
        }

        binding.btnApply.setOnClickListener {
            val filterValue = filterAdapter.getFilterValue()
            val sortList = sortAdapter.getSortValue()

            println("filterList is:: $filterValue and sortList is:: $sortList")
        }

    }
}