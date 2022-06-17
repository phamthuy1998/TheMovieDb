package com.thuypham.ptithcm.baseapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.model.HomeCategory
import com.thuypham.ptithcm.baseapp.model.HomeCategoryData
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.extension.getDataFromJsonRawResource
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.local.IStorage
import com.thuypham.ptithcm.data.local.SharedPreferencesStorage
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.domain.repository.MovieRepository
import com.thuypham.ptithcm.domain.repository.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val peopleRepository: PeopleRepository,
    private val sharedPrf: IStorage
) : BaseViewModel() {

    private var homeCategoryItems: ArrayList<HomeCategoryData> = arrayListOf()
    private val _homeCategories = MutableLiveData<ArrayList<HomeCategoryData>>()
    val homeCategories: LiveData<ArrayList<HomeCategoryData>> = _homeCategories

    fun getAllDataHome(context: WeakReference<Context>) = viewModelScope.launch(Dispatchers.IO) {

        val isFirstTimeOpenApp = sharedPrf.getBoolean(SharedPreferencesStorage.IS_FIRST_TIME_OPEN_APP, true)

        if (isFirstTimeOpenApp) {
            val tempHomeCategory = getDataFromJsonRawResource<ArrayList<HomeCategory>>(context, R.raw.home_category_config)
            // Save home config to Database
            logD("getAllDataHome: $tempHomeCategory")
            // Convert HomeCategory to HomeCategoryData
            tempHomeCategory?.forEachIndexed { index, homeCategory ->
                val homeCategoryData = homeCategory.toHomeCategoryData(index, context)
                homeCategoryItems.add(homeCategoryData)
            }
            _homeCategories.postValue(homeCategoryItems)
            getAllItemCategory()

            // Todo: Update IS_FIRST_TIME_OPEN_APP to false
        } else {
            // Todo: Load data from database
            _homeCategories.postValue(homeCategoryItems)
        }
    }

    private suspend fun getAllItemCategory() {
        logD("getAllItemCategory")
        viewModelScope.launch(Dispatchers.IO) {
            homeCategoryItems.map { categoryData ->
                async(Dispatchers.IO) {
                    categoryData.listItems = getItemCategory(categoryData)
                }
            }.awaitAll()
            _homeCategories.postValue(homeCategoryItems)
        }
    }

    private suspend fun getItemCategory(categoryData: HomeCategoryData): ArrayList<Any>? {
        return when (categoryData.type) {
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
                getMovieGenres()
            }
            HomeCategoryType.POPULAR_PEOPLE -> {
                getPopularPeople()
            }
            else -> {
                null
            }
        }
    }


    private var currentTrendingPage = 1
    private suspend fun getMovieTrending(): ArrayList<Any>? {
        val result = movieRepository.getMovieTrending(currentTrendingPage)
        logD("getMovieTrending: $result")
        currentTrendingPage++
        return handleMovieListResponse(result)
    }

    private var currentNowPlayingPage = 1
    private suspend fun getMovieNowPlaying(): ArrayList<Any>? {
        val result = movieRepository.getMovieNowPlaying(currentNowPlayingPage)
        logD("getMovieNowPlaying: $result")
        currentNowPlayingPage++
        return handleMovieListResponse(result)
    }

    private var currentMovieUpComingPage = 1
    private suspend fun getMovieUpComing(): ArrayList<Any>? {
        val result = movieRepository.getMovieUpComing(currentMovieUpComingPage)
        logD("getMovieUpComing: $result")
        currentMovieUpComingPage++
        return handleMovieListResponse(result)
    }

    private var currentMoviePopularPage = 1
    private suspend fun getMoviePopular(): ArrayList<Any>? {
        val result = movieRepository.getMoviePopular(currentMoviePopularPage)
        logD("getMoviePopular: $result")
        currentMoviePopularPage++
        return handleMovieListResponse(result)
    }

    private var currentMovieTopRatePage = 1
    private suspend fun getMovieTopRate(): ArrayList<Any>? {
        val result = movieRepository.getMovieTopRate(currentMovieTopRatePage)
        logD("getMovieTopRate: $result")
        currentMovieTopRatePage++
        return handleMovieListResponse(result)
    }

    private suspend fun getMovieGenres(): ArrayList<Any>? {
        return when (val result = movieRepository.getMovieGenres()) {
            is ResponseHandler.Success -> {
                (result.data.genres) as ArrayList<Any>?
            }
            is ResponseHandler.Error -> {
                null
            }
            else -> {
                null
            }
        }
    }

    private var currentPopularPeoplePage = 1
    private suspend fun getPopularPeople(): ArrayList<Any>? {
        val result = peopleRepository.getPopularPeople(currentPopularPeoplePage)
        currentPopularPeoplePage++
        return when (result) {
            is ResponseHandler.Success -> {
                result.data.results as ArrayList<Any>?
            }
            is ResponseHandler.Error -> {
                null
            }
            else -> {
                null
            }
        }
    }


    private fun handleMovieListResponse(result: ResponseHandler<ListResponse<Movie>>): ArrayList<Any>? {
        return when (result) {
            is ResponseHandler.Success -> {
                result.data.results as ArrayList<Any>?
            }
            is ResponseHandler.Error -> {
                null
            }
            else -> {
                null
            }
        }
    }

}