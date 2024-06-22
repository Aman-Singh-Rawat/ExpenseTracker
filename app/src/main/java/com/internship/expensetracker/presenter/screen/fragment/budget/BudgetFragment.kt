package com.internship.expensetracker.presenter.screen.fragment.budget

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentBudgetBinding
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity

class BudgetFragment : Fragment() {
    private lateinit var binding: FragmentBudgetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.statusBarColor = resources.getColor(R.color.violate, null)
        super.onViewCreated(view, savedInstanceState)
    }
}