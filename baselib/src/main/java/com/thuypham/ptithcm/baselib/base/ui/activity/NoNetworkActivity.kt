package com.thuypham.ptithcm.baselib.base.ui.activity

import android.content.Context
import android.content.Intent
import com.thuypham.ptithcm.baselib.R
import com.thuypham.ptithcm.baselib.base.base.CommonBaseActivity
import com.thuypham.ptithcm.baselib.databinding.ActivityNoNetworkBinding

class NoNetworkActivity : CommonBaseActivity<ActivityNoNetworkBinding>(R.layout.activity_no_network) {

    companion object {
        var isShow = false
        fun start(context: Context?) {
            if (!isShow)
                context?.let {
                    val intent = Intent(it, NoNetworkActivity::class.java)
                    it.startActivity(intent)
                }
            isShow = true
        }
    }

    override fun setupView() {

    }

    override fun onNetworkAvailable() {
        super.onNetworkAvailable()
        finish()
    }

    override fun onBackPressed() {

    }

    override fun onDestroy() {
        super.onDestroy()
        isShow = false
    }
}