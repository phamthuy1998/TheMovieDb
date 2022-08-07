package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.data.local.IStorage
import com.thuypham.ptithcm.data.local.SharedPreferencesStorage
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieCategoryViewModel(
    private val movieRepository: MovieRepository,
    private val sharedPrf: IStorage
) : BaseViewModel() {

    var movieListPaging: LiveData<PagingData<Movie>> = MutableLiveData()

    fun getMovieItems(movieType: String, genreID: Int) = viewModelScope.launch() {
        logD("getMovieItems - movieType: $movieType, genreID - $genreID")
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
            HomeCategoryType.MOVIE_GENRES -> {
                getMovieByGenreId(genreID)
            }
            else -> {
                logD("getMovieItems-value = null")
                movieListPaging = MutableLiveData<PagingData<Movie>>(null).cachedIn(viewModelScope)
            }
        }
    }

    private suspend fun getMovieByGenreId(genreID: Int) {
        logD("getMovieByGenreId")
        movieListPaging = movieRepository.getMovieByGenreId(genreID).cachedIn(viewModelScope)
    }


    private suspend fun getMovieTrending() {
        logD("MovieTrending")
        movieListPaging = movieRepository.getMovieTrendingPaging().cachedIn(viewModelScope)
    }

    private suspend fun getMovieNowPlaying() {
        logD("getMovieNowPlaying")
        movieListPaging = movieRepository.getMovieNowPlayingPaging().cachedIn(viewModelScope)
    }

    private suspend fun getMovieUpComing() {
        logD("getMovieUpComing")
        movieListPaging = movieRepository.getMovieUpComingPaging().cachedIn(viewModelScope)
    }

    private suspend fun getMoviePopular() {
        logD("getMoviePopular")
        movieListPaging = movieRepository.getMoviePopularPaging().cachedIn(viewModelScope)
    }

    private suspend fun getMovieTopRate() {
        logD("getMovieTopRate")
        movieListPaging = movieRepository.getMovieTopRatePaging().cachedIn(viewModelScope)
    }

    fun isShowGridLayout(): Boolean {
        return sharedPrf.getBoolean(SharedPreferencesStorage.IS_RECYCLERVIEW_LAYOUT_GRID_VIEW, true)
    }

    fun saveRecyclerViewMode(isRecyclerviewGridLayout: Boolean) {
        sharedPrf.setBoolean(SharedPreferencesStorage.IS_RECYCLERVIEW_LAYOUT_GRID_VIEW, isRecyclerviewGridLayout)
    }

}