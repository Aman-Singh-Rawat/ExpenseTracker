package com.internship.expensetracker.presenter.screen.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.ibBackPressed.setOnClickListener {
            requireActivity().finish()
        }
        binding.tvLogin.setOnClickListener { findNavController().navigate(R.id.loginFragment) }
        return binding.root
    }
}