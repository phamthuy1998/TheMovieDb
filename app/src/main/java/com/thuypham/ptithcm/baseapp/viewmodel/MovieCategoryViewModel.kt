package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieCategoryViewModel(
    private val movieRepository: MovieRepository,
) : BaseViewModel() {

    var movieListPaging: LiveData<PagingData<Movie>>? = null

    fun getMovieItems(movieType: String) = viewModelScope.launch(Dispatchers.IO) {
        logD("getMovieItems")
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

            }
        }
    }


    private suspend fun getMovieTrending() {
        movieListPaging = movieRepository.getMovieTrendingPaging().cachedIn(viewModelScope)
    }

    private suspend fun getMovieNowPlaying() {
        movieListPaging = movieRepository.getMovieNowPlayingPaging().cachedIn(viewModelScope)
    }

    private suspend fun getMovieUpComing() {
        movieListPaging = movieRepository.getMovieUpComingPaging().cachedIn(viewModelScope)
    }

    private suspend fun getMoviePopular() {
        movieListPaging = movieRepository.getMoviePopularPaging().cachedIn(viewModelScope)
    }

    private suspend fun getMovieTopRate() {
        movieListPaging = movieRepository.getMovieTopRatePaging().cachedIn(viewModelScope)
    }

}