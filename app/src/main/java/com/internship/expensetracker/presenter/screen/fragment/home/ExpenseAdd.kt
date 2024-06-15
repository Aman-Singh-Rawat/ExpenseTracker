package com.internship.expensetracker.presenter.screen.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentExpenseAddBinding

class ExpenseAdd : Fragment() {
    private lateinit var binding: FragmentExpenseAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenseAddBinding.inflate(inflater, container, false)
        return binding.root
    }
}