package com.thuypham.ptithcm.baseapp.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.thuypham.ptithcm.baseapp.util.ToolbarHelper
import com.thuypham.ptithcm.baselib.base.base.CommonBaseFragment

abstract class BaseFragment<T : ViewDataBinding>(layoutId: Int) : CommonBaseFragment<T>(layoutId) {

    protected lateinit var toolbarHelper: ToolbarHelper


    companion object {
        val TAG = this::class.java.name
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarHelper = ToolbarHelper(binding.root)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbarHelper.clear()
        binding.unbind()
    }
}