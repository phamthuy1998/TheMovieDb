package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.data.remote.response.Person
import com.thuypham.ptithcm.domain.repository.PeopleRepository
import kotlinx.coroutines.launch

class PeopleViewModel(private val peopleRepository: PeopleRepository) : BaseViewModel() {
    var peoplePaging: LiveData<PagingData<Person>>? = null

    fun getPopularPeople() {
        viewModelScope.launch {
            peoplePaging = peopleRepository.getPopularPeoplePaging().cachedIn(viewModelScope)
        }
    }
}