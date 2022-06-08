package com.thuypham.ptithcm.baseapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseActivity
import com.thuypham.ptithcm.baseapp.databinding.ActivitySplashBinding
import com.thuypham.ptithcm.data.util.ApiHelper
import com.thuypham.ptithcm.baselib.base.extension.logD

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun setupView() {
        logD("apiaaaa: ${ApiHelper.movieApiKey()} ")
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}