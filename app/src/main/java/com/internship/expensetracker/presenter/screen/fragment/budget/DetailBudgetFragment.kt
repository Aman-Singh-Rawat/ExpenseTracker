package com.internship.expensetracker.presenter.screen.fragment.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentDetailBudgetBinding
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity

class DetailBudgetFragment : Fragment() {
    private lateinit var binding: FragmentDetailBudgetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.statusBarColor = resources.getColor(R.color.white, null)
        super.onViewCreated(view, savedInstanceState)

        binding.icFragDetailArrow.setOnClickListener {
            (activity as HomeContainerActivity).onBackPressed()
        }
    }
}