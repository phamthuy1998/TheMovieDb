package com.thuypham.ptithcm.baseapp.base

import androidx.databinding.ViewDataBinding
import com.thuypham.ptithcm.baseapp.util.ToolbarHelper
import com.thuypham.ptithcm.baselib.base.base.CommonBaseActivity

abstract class BaseActivity<T : ViewDataBinding>(private val layoutId: Int) : CommonBaseActivity<T>(layoutId) {

    protected val toolbarHelper by lazy { binding?.root?.let { ToolbarHelper(it) } }

    override fun onDestroy() {
        super.onDestroy()
        toolbarHelper?.clear()
    }
}