package com.internship.expensetracker.presenter.screen.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentDetailTransactionBinding
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity

class DetailTransaction : Fragment() {
    private lateinit var binding: FragmentDetailTransactionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.red, null)
        binding.icFragDetailArrow.setOnClickListener {
            (activity as HomeContainerActivity).onBackPressed()
        }

        binding.imgTrash.setOnClickListener { findNavController().navigate(R.id.removeTransactionBottomSheetFragment) }
    }
}