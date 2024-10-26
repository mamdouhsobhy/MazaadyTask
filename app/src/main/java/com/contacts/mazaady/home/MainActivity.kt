package com.contacts.mazaady.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.contacts.mazaady.R
import com.contacts.mazaady.core.presentation.base.BaseActivity
import com.contacts.mazaady.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        initiateBottomNavigation()

    }

    private fun initialize() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

            }
        }

    }

    private fun initiateBottomNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
        animateUp(binding.bottomNavigationView)
    }
}