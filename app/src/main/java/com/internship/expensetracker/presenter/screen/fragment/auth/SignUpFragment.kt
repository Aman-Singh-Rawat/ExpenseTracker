package com.internship.expensetracker.presenter.screen.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.core.BaseFragment
import com.internship.expensetracker.databinding.FragmentSignUpBinding
import com.internship.expensetracker.presenter.viewmodel.SignUpViewmodel

class SignUpFragment : BaseFragment() {
    private val viewModel: SignUpViewmodel by viewModels()
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibBackPressed.setOnClickListener {
            requireActivity().finish()
        }
        binding.btnSignUp.setOnClickListener {
            setUpLogin()
        }
        binding.tvLogin.setOnClickListener { findNavController().navigate(R.id.loginFragment) }
    }

    private fun setUpLogin() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (validationCheck(name, email, password)) {
            showProgress()
            viewModel.createUser(name, email, password, {
                hideProgress()
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginFragment)
            }) {
                hideProgress()
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validationCheck(name: String, email: String, password: String): Boolean {
        when {
            name.isEmpty() -> {
                showToast("Name is not to be empty! Please enter name")
                return false
            }
            email.isEmpty() -> {
                showToast("Email is not to be empty! Please enter email")
                return false
            }
            password.isEmpty() -> {
                showToast("Password is not to be empty! Please enter Password")
                return false
            }
            name.isNotEmpty() && email.isNotEmpty()
                    && password.isNotEmpty() -> return true

            else -> {
                showToast("You are too lazy enter all values")
                return false
            }
        }
    }
}