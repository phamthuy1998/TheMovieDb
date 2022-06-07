package com.thuypham.ptithcm.baselib.base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<Exception>()
    val isLoading = MutableLiveData<Boolean>()
}