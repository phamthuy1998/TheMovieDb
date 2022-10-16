package com.thuypham.ptithcm.baseapp.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.util.ToolbarHelper
import com.thuypham.ptithcm.baselib.base.base.CommonBaseFragment
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import kotlin.math.log

abstract class BaseFragment<T : ViewDataBinding>(layoutId: Int) : CommonBaseFragment<T>(layoutId) {

    protected lateinit var toolbarHelper: ToolbarHelper
    protected val glide by lazy { Glide.with(this) }


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

    fun <Data> LiveData<ResponseHandler<Data>>.observerData(onResult: ((dataRes: Data) -> Unit?)? = null) {
        this.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseHandler.Loading -> {
                    logD("showLoading - observerData: ${result}")
                    showLoading()
                }
                is ResponseHandler.Error -> {
                    hideLoading()
                    showSnackBar(result.message)
                }
                is ResponseHandler.Success -> {
                    hideLoading()
                    onResult?.invoke(result.data)
                }
            }
        }
    }

}