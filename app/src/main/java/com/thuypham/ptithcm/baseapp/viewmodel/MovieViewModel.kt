package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.*
import com.thuypham.ptithcm.domain.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    var movieId = 0
    var movieDetail: MovieDetail? = null
    private val _movieDetail = MutableLiveData<ResponseHandler<MovieDetail>>(ResponseHandler.Loading)
    val movieDetailResponse: LiveData<ResponseHandler<MovieDetail>> get() = _movieDetail

    val movieImages = MutableLiveData<ResponseHandler<MovieImage>>(ResponseHandler.Loading)
    val movieVideo = MutableLiveData<ResponseHandler<MovieVideo>>(ResponseHandler.Loading)


    var moviesResponse: LiveData<PagingData<Movie>> = MutableLiveData()
    var movieReview: LiveData<PagingData<Review>> = MutableLiveData()
    var movieCastData: MutableLiveData<ResponseHandler<MovieCast>> = MutableLiveData(ResponseHandler.Loading)

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        _movieDetail.value = movieRepository.getMovieDetail(movieId)
    }

    fun getMovieImages(movieId: Int) = viewModelScope.launch {
        movieImages.value = movieRepository.getMovieImages(movieId)
    }

    fun getMovieVideo() = viewModelScope.launch {
        logD("getMovieVideo")
        movieVideo.value = movieRepository.getMovieVideo(movieId)
    }

    fun getMovieRecommendation() = viewModelScope.launch {
        moviesResponse = movieRepository.getMoviesRecommendationPaging(movieId).cachedIn(viewModelScope)
    }

    fun getSimilarMovies() = viewModelScope.launch {
        moviesResponse = movieRepository.getSimilarMoviesPaging(movieId).cachedIn(viewModelScope)
    }

    fun getMovieCast() = viewModelScope.launch {
        logD("getMovieCast: movieID: $movieId")
        movieCastData.value = movieRepository.getMovieCast(movieId)
    }

    fun getMovieReview() = viewModelScope.launch {
        movieReview = movieRepository.getMovieReview(movieId).cachedIn(viewModelScope)
    }

    fun clearData() {
        onCleared()
    }
}