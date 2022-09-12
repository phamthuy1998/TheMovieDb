package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.MovieDetail
import com.thuypham.ptithcm.domain.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    private val _movieDetail = MutableLiveData<ResponseHandler<MovieDetail>>()
    val movieDetail: LiveData<ResponseHandler<MovieDetail>> get() = _movieDetail


    private val _moviesResponse = MutableLiveData<List<Movie>?>()
    val moviesResponse: LiveData<List<Movie>?> get() = _moviesResponse

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        _movieDetail.value = movieRepository.getMovieDetail(movieId)
    }

    fun getMovieRecommendation(movieId: Int) = viewModelScope.launch {
        when (val result = movieRepository.getMoviesRecommendation(movieId)) {
            is ResponseHandler.Success -> {
                _moviesResponse.value = result.data.results
            }
            is ResponseHandler.Error -> {
                errorLiveData.value = result
            }
            else -> {}
        }
    }

    fun getSimilarMovies(movieId: Int) = viewModelScope.launch {
        when (val result = movieRepository.getSimilarMovies(movieId)) {
            is ResponseHandler.Success -> {
                _moviesResponse.value = result.data.results
            }
            is ResponseHandler.Error -> {
                errorLiveData.value = result
            }
            else -> {}
        }
    }
}