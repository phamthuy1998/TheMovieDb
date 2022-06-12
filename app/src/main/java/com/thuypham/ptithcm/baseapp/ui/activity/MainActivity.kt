package com.thuypham.ptithcm.baseapp.ui.activity

import android.view.MenuItem
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationBarView
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseActivity
import com.thuypham.ptithcm.baseapp.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), NavigationBarView.OnItemSelectedListener {

    private val navController by lazy {
        findNavController(R.id.mainNavHostFragment)
    }

    override fun setupView() {
//        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        binding?.run {
            bottomNav.setOnItemSelectedListener(this@MainActivity)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
                navController.navigate(R.id.homeFragment)
            }
            R.id.searchFragment -> {
                navController.navigate(R.id.searchFragment)
            }
            R.id.watchlistFragment -> {
                navController.navigate(R.id.watchlistFragment)
            }
            R.id.profileFragment -> {
                navController.navigate(R.id.profileFragment)
            }
        }
        return true
    }


}