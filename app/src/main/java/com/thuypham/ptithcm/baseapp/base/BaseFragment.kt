package com.thuypham.ptithcm.baseapp.base

import androidx.databinding.ViewDataBinding
import com.thuypham.ptithcm.baseapp.util.ToolbarHelper
import com.thuypham.ptithcm.baselib.base.base.CommonBaseFragment

abstract class BaseFragment<T : ViewDataBinding>(private val layoutId: Int) : CommonBaseFragment<T>(layoutId) {

    protected val toolbarHelper by lazy { ToolbarHelper(binding.root) }
}