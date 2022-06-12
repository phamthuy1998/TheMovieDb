package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baseapp.model.LoadingItem
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.MovieResponse
import com.thuypham.ptithcm.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieCategoryViewModel(
    private val movieRepository: MovieRepository,
) : BaseViewModel() {

    private val _movieList = MutableLiveData<List<Movie>?>()
    val movieList: LiveData<List<Movie>?> get() = _movieList
    private val movieListItems: ArrayList<Any> = arrayListOf()

    private var currentPage = 1
    var isLoadMoreAble = true


    fun getMovieItems(movieType: String) = viewModelScope.launch(Dispatchers.IO) {
        logD("getMovieItems")
        movieListItems.add(LoadingItem())
        when (movieType) {
            HomeCategoryType.MOVIE_TRENDING -> {
                getMovieTrending()
            }
            HomeCategoryType.MOVIE_NOW_PLAYING -> {
                getMovieNowPlaying()
            }
            HomeCategoryType.MOVIE_UPCOMING -> {
                getMovieUpComing()
            }
            HomeCategoryType.MOVIE_POPULAR -> {
                getMoviePopular()
            }
            HomeCategoryType.MOVIE_TOP_RATE -> {
                getMovieTopRate()
            }
            else -> {
                _movieList.postValue(null)
            }
        }
    }


    private suspend fun getMovieTrending() {
        val result = movieRepository.getMovieTrending(currentPage)
        logD("getMovieTrending: $result")
        handleMovieListResponse(result)
    }

    private suspend fun getMovieNowPlaying() {
        val result = movieRepository.getMovieNowPlaying(currentPage)
        logD("getMovieNowPlaying: $result")
        handleMovieListResponse(result)
    }

    private suspend fun getMovieUpComing() {
        val result = movieRepository.getMovieUpComing(currentPage)
        logD("getMovieUpComing: $result")
        handleMovieListResponse(result)
    }

    private suspend fun getMoviePopular() {
        val result = movieRepository.getMoviePopular(currentPage)
        logD("getMoviePopular: $result")
        handleMovieListResponse(result)
    }

    private suspend fun getMovieTopRate() {
        val result = movieRepository.getMovieTopRate(currentPage)
        logD("getMovieTopRate: $result")
        handleMovieListResponse(result)
    }

    private fun handleMovieListResponse(result: ResponseHandler<MovieResponse>) {
        when (result) {
            is ResponseHandler.Success -> {
                if (result.data.totalPages == currentPage) {
                    logD("Stop Load More")
                    isLoadMoreAble = false
                }

                // Remove Loading Item
                movieListItems.removeLast()

                movieListItems.addAll(result.data.results ?: return)
                _movieList.postValue(movieListItems as List<Movie>)
            }
            is ResponseHandler.Failure -> {
                errorLiveData.postValue(result)
            }
            else -> {}
        }
        currentPage++
    }
}