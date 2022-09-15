package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.MovieDetail
import com.thuypham.ptithcm.domain.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    private val _movieDetail = MutableLiveData<ResponseHandler<MovieDetail>>()
    val movieDetail: LiveData<ResponseHandler<MovieDetail>> get() = _movieDetail


    var moviesResponse: LiveData<PagingData<Movie>> = MutableLiveData()

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        _movieDetail.value = movieRepository.getMovieDetail(movieId)
    }

    fun getMovieRecommendation(movieId: Int) = viewModelScope.launch {
        moviesResponse = movieRepository.getMoviesRecommendationPaging(movieId).cachedIn(viewModelScope)
    }

    fun getSimilarMovies(movieId: Int) = viewModelScope.launch {
        moviesResponse = movieRepository.getSimilarMoviesPaging(movieId).cachedIn(viewModelScope)
    }
}