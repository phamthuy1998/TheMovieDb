package com.thuypham.ptithcm.baselib.base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler

abstract class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<ResponseHandler.Error>()
    val isLoading = MutableLiveData<Boolean>()
}