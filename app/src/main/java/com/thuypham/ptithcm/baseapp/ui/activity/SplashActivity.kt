package com.thuypham.ptithcm.baseapp.ui.activity

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseActivity
import com.thuypham.ptithcm.baseapp.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val animationListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {}

        override fun onAnimationEnd(p0: Animator?) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        override fun onAnimationCancel(p0: Animator?) {}

        override fun onAnimationRepeat(p0: Animator?) {}
    }

    override fun setupView() {
        binding.animationView.addAnimatorListener(animationListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.animationView.removeAllAnimatorListeners()
    }
}