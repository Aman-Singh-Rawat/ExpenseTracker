package com.internship.expensetracker.presenter.screen.fragment.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentCreateBudgetBinding
import com.internship.expensetracker.presenter.adapters.CustomSpinnerAdapter

class CreateBudgetFragment : Fragment() {
    private lateinit var binding: FragmentCreateBudgetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerItems = listOf("Aman", "Rajveer", "Tanuj", "Vipul")
        val adapter = CustomSpinnerAdapter(requireContext(), spinnerItems)
        binding.spinnerCategory.adapter = adapter
    }
}