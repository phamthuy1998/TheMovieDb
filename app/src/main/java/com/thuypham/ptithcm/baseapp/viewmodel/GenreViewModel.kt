package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.MovieGenre
import com.thuypham.ptithcm.domain.usecase.movie.GetMovieGenresUseCase
import kotlinx.coroutines.launch

class GenreViewModel(private val genresUseCase: GetMovieGenresUseCase) : BaseViewModel() {

    private val _genres = MutableLiveData<List<MovieGenre>>()
    val genres: LiveData<List<MovieGenre>> get() = _genres

    fun getGenres() {
        viewModelScope.launch {
            when (val result = genresUseCase.invoke()) {
                is ResponseHandler.Success -> {
                    _genres.value = result.data.genres ?: arrayListOf()
                }
                is ResponseHandler.Error -> {
                    errorLiveData.value = result
                }
                else -> {

                }
            }
        }
    }
}