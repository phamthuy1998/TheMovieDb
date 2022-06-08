package com.thuypham.ptithcm.baseapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseActivity
import com.thuypham.ptithcm.baseapp.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun setupView() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}