package com.internship.expensetracker.presenter.screen.fragment.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.core.BaseFragment
import com.internship.expensetracker.databinding.FragmentForgetPasswordBinding
import com.internship.expensetracker.presenter.viewmodel.LoginViewmodel

class ForgetPassword : BaseFragment() {
    private lateinit var binding: FragmentForgetPasswordBinding
    private val viewModel: LoginViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        binding.ibBackPressed.setOnClickListener { findNavController().navigateUp() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            if (binding.etEmail.text?.isNotBlank() == true) {
                showProgress()
                viewModel.forgetPassword(binding.etEmail.text.toString(), {
                    findNavController().navigate(R.id.action_forgetPasswordFragment_to_forgetSuccessPassword)
                    hideProgress()
                }) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    binding.btnSignUp.isClickable = true
                    hideProgress()
                }
            } else
                showToast("Please Don't be lazy enter email")
        }
    }
}