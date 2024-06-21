package com.internship.expensetracker.presenter.screen.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentDetailTransactionBinding

class DetailTransaction : Fragment() {
    private lateinit var binding: FragmentDetailTransactionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }
}