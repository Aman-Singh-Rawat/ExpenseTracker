package com.internship.expensetracker.presenter.screen.fragment.auth

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.core.BaseFragment
import com.internship.expensetracker.databinding.FragmentLoginBinding
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity
import com.internship.expensetracker.presenter.viewmodel.LoginViewmodel
class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.ibBackPressed.setOnClickListener {
            requireActivity().finish()
        }
        binding.forgotPassword.setOnClickListener { findNavController().navigate(R.id.forgetPasswordFragment) }
        binding.tvSignUp.setOnClickListener { findNavController().navigate(R.id.signUpFragment) }
        binding.btnSignUp.setOnClickListener { loginUser() }
        return binding.root
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            showProgress()
            viewModel.loginUser(email, password, {
                if (isAdded && !isRemoving) {
                    showToast(it)
                    val intent = Intent(requireActivity(), HomeContainerActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    hideProgress()
                }
            }) {errorMessage ->
                if (isAdded && !isRemoving) {
                    showToast(errorMessage)
                    hideProgress()
                }
            }
        } else showToast("Email and password must not be empty")
    }


}