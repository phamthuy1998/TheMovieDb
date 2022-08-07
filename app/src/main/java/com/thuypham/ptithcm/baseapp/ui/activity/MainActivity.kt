package com.thuypham.ptithcm.baseapp.ui.activity

import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseActivity
import com.thuypham.ptithcm.baseapp.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val navController by lazy {
        findNavController(R.id.mainNavHostFragment)
    }

    override fun setupView() {
        binding?.run {
            // Setup bottom navigation
            NavigationUI.setupWithNavController(bottomNav, navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->

                val currentFragmentId = destination.id
                val shouldShowBottomNav = currentFragmentId == R.id.homeFragment || currentFragmentId == R.id.searchFragment ||
                        currentFragmentId == R.id.watchlistFragment || currentFragmentId == R.id.profileFragment
                bottomNav.isVisible = shouldShowBottomNav
            }
        }
    }


}