package com.thuypham.ptithcm.baseapp.ui.activity

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseActivity
import com.thuypham.ptithcm.baseapp.databinding.ActivityMainBinding
import com.thuypham.ptithcm.baselib.base.extension.logD


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), NavController.OnDestinationChangedListener {

    private val navController by lazy {
        findNavController(R.id.mainNavHostFragment)
    }

    override fun setupView() {
        binding?.run {
            // Setup bottom navigation
            NavigationUI.setupWithNavController(bottomNav, navController)
            navController.addOnDestinationChangedListener(this@MainActivity)
        }
    }

    override fun clearData() {
        super.clearData()
        navController.removeOnDestinationChangedListener(this@MainActivity)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        val currentFragmentId = destination.id
        val shouldShowBottomNav = currentFragmentId == R.id.homeFragment || currentFragmentId == R.id.searchFragment ||
                currentFragmentId == R.id.watchlistFragment || currentFragmentId == R.id.profileFragment
        logD("onDestinationChanged: shouldShowBottomNav = $shouldShowBottomNav")
        binding?.bottomNav?.isVisible = shouldShowBottomNav
    }


}