package com.thuypham.ptithcm.baseapp.viewmodel

import android.text.method.TextKeyListener.clear
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thuypham.ptithcm.baseapp.di.viewModelModule
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.MovieDetail
import com.thuypham.ptithcm.data.remote.response.MovieImage
import com.thuypham.ptithcm.data.remote.response.MovieVideo
import com.thuypham.ptithcm.domain.repository.MovieRepository
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModelFactory

class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    var movieId = 0
    var movieDetail: MovieDetail? = null
    private val _movieDetail = MutableLiveData<ResponseHandler<MovieDetail>>(ResponseHandler.Loading)
    val movieDetailResponse: LiveData<ResponseHandler<MovieDetail>> get() = _movieDetail

    val movieImages = MutableLiveData<ResponseHandler<MovieImage>>(ResponseHandler.Loading)
    val movieVideo = MutableLiveData<ResponseHandler<MovieVideo>>(ResponseHandler.Loading)


    var moviesResponse: LiveData<PagingData<Movie>> = MutableLiveData()

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        _movieDetail.value = movieRepository.getMovieDetail(movieId)
    }

    fun getMovieImages(movieId: Int) = viewModelScope.launch {
        movieImages.value = movieRepository.getMovieImages(movieId)
    }

    fun getMovieVideo(movieID: Int) = viewModelScope.launch {
        logD("getMovieVideo")
        movieVideo.value = movieRepository.getMovieVideo(movieID)
    }

    fun getMovieRecommendation(movieId: Int) = viewModelScope.launch {
        moviesResponse = movieRepository.getMoviesRecommendationPaging(movieId).cachedIn(viewModelScope)
    }

    fun getSimilarMovies(movieId: Int) = viewModelScope.launch {
        moviesResponse = movieRepository.getSimilarMoviesPaging(movieId).cachedIn(viewModelScope)
    }

    fun clearData() {
        onCleared()
    }
}