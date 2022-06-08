package com.thuypham.ptithcm.baseapp.ui.activity

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseActivity
import com.thuypham.ptithcm.baseapp.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun setupView() {
        val navController = findNavController(R.id.mainNavHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }


}