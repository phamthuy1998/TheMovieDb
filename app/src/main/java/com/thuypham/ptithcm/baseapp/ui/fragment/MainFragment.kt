package com.thuypham.ptithcm.baseapp.ui.fragment

import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationBarView
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMainBinding
import com.thuypham.ptithcm.baselib.base.extension.logD

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main), NavigationBarView.OnItemSelectedListener {
    private val navController by lazy {
        findNavController()
    }

    override fun setupView() {
        logD("setupView")
//        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        binding.run {
            bottomNav.setOnItemSelectedListener(this@MainFragment)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController.navigate(item.itemId)
        return true
    }
}