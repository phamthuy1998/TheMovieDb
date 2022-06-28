package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.Person
import com.thuypham.ptithcm.data.remote.response.PersonDetail
import com.thuypham.ptithcm.data.remote.response.PersonImage
import com.thuypham.ptithcm.domain.repository.PeopleRepository
import kotlinx.coroutines.launch

class PersonViewModel(private val peopleRepository: PeopleRepository) : BaseViewModel() {

    private val _personImages = MutableLiveData<PersonImage?>()
    val personImages: LiveData<PersonImage?> get() = _personImages


    private val _personDetail = MutableLiveData<PersonDetail?>()
    val personDetail: LiveData<PersonDetail?> get() = _personDetail

    private val _personInfo = MutableLiveData<Person?>()
    val personInfo: LiveData<Person?> get() = _personInfo


    private val _movieResponse = MutableLiveData<List<Movie>?>()
    val movieResponse: LiveData<List<Movie>?> get() = _movieResponse

    private val _tvShowsResponse = MutableLiveData<List<Movie>?>()
    val tvShowsResponse: LiveData<List<Movie>?> get() = _tvShowsResponse

    fun setPersonInfo(person: Person?) {
        _personInfo.value = person
    }

    private var personId = 0

    fun setPersonId(_personId: Int) {
        personId = _personId
    }

    fun getPersonDetail() = viewModelScope.launch {
        when (val result = peopleRepository.getPersonDetail(personId)) {
            is ResponseHandler.Success -> {
                _personDetail.value = result.data
            }
            is ResponseHandler.Error -> {
                errorLiveData.value = result
            }
        }
    }

    fun getMovieList() = viewModelScope.launch {
        when (val result = peopleRepository.getPersonMovie(personId)) {
            is ResponseHandler.Success -> {
                _movieResponse.value = result.data.cast
            }
            is ResponseHandler.Error -> {
                errorLiveData.value = result
            }
        }
    }

    fun getTVShow() = viewModelScope.launch {
        when (val result = peopleRepository.getPersonTVShows(personId)) {
            is ResponseHandler.Success -> {
                _tvShowsResponse.value = result.data.cast
            }
            is ResponseHandler.Error -> {
                errorLiveData.value = result
            }
        }
    }


    fun getPersonImage() = viewModelScope.launch {
        when (val result = peopleRepository.getPersonImage(personId)) {
            is ResponseHandler.Success -> {
                _personImages.value = result.data
            }
            is ResponseHandler.Error -> {
                errorLiveData.value = result
            }
        }
    }
}