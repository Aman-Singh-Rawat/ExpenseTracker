package com.internship.expensetracker.presenter.screen.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.replace
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navigation
import com.internship.expensetracker.R
import com.internship.expensetracker.presenter.screen.fragment.auth.LoginFragment
import com.internship.expensetracker.presenter.screen.fragment.auth.SignUpFragment

class AuthContainerFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth_container)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragmentContainerView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val inflater: NavInflater = navController.navInflater
        val graph: NavGraph = inflater.inflate(R.navigation.navigation_auth)

        val data = intent.getStringExtra("user")
        if (data != null) {
            graph.setStartDestination(
                if (data == "login") R.id.loginFragment
                else R.id.signUpFragment
            )
            navController.graph = graph
        }
    }
}