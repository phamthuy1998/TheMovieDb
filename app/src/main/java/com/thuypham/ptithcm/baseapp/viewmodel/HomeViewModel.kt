package com.thuypham.ptithcm.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thuypham.ptithcm.baseapp.MainApplication
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.data.local.IStorage
import com.thuypham.ptithcm.baseapp.data.local.SharedPreferencesStorage
import com.thuypham.ptithcm.baseapp.data.remote.response.MovieResponse
import com.thuypham.ptithcm.baseapp.domain.usecase.movie.*
import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.baselib.base.extension.getDataFromJsonRawResource
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.baseapp.model.HomeCategory
import com.thuypham.ptithcm.baseapp.model.HomeCategoryData
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baseapp.ui.adapter.ItemModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getMovieTrendingUseCase: GetMovieTrendingUseCase,
    private val getMovieNowPlayingUseCase: GetMovieNowPlayingUseCase,
    private val getMovieUpComingUseCase: GetMovieUpComingUseCase,
    private val getMoviePopularUseCase: GetMoviePopularUseCase,
    private val getMovieTopRateUseCase: GetMovieTopRateUseCase,
    private val sharedPrf: IStorage
) : BaseViewModel() {

    private var homeCategoryItems: ArrayList<HomeCategoryData> = arrayListOf()
    private val _homeCategories = MutableLiveData<ArrayList<HomeCategoryData>>()
    val homeCategories: LiveData<ArrayList<HomeCategoryData>> = _homeCategories

    private val _notifyItemChangePosition = MutableLiveData<Int>()
    val notifyItemChangePosition: LiveData<Int> get() = _notifyItemChangePosition

    fun getAllDataHome() = viewModelScope.launch {
        val context = MainApplication.applicationContext()

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
            _homeCategories.value = homeCategoryItems
            getAllItemCategory()

            // Todo: Update IS_FIRST_TIME_OPEN_APP to false
        } else {
            // Todo: Load data from database
            _homeCategories.value = homeCategoryItems
        }
    }

    private fun getAllItemCategory() {
        logD("getAllItemCategory")
        homeCategoryItems.forEach { categoryData ->
            getItemCategory(categoryData)
        }
    }

    private fun getItemCategory(categoryData: HomeCategoryData) {
        when (categoryData.type) {
            HomeCategoryType.MOVIE_TRENDING -> {
                getMovieTrending(categoryData)
            }
            HomeCategoryType.MOVIE_NOW_PLAYING -> {
                getMovieNowPlaying(categoryData)
            }
            HomeCategoryType.MOVIE_UPCOMING -> {
                getMovieUpComing(categoryData)
            }
            HomeCategoryType.MOVIE_POPULAR -> {
                getMoviePopular(categoryData)
            }
            HomeCategoryType.MOVIE_TOP_RATE -> {
                getMovieTopRate(categoryData)
            }
            HomeCategoryType.MOVIE_GENRES -> {
                getMovieGenres(categoryData)
            }
        }
    }

    private var currentTrendingPage = 1
    private fun getMovieTrending(categoryData: HomeCategoryData) = viewModelScope.launch {
        val result = getMovieTrendingUseCase.invoke(currentTrendingPage)
        logD("getMovieTrending: $result")
        handleMovieListResponse(result, categoryData)
        currentTrendingPage++
    }

    private var currentNowPlayingPage = 1
    private fun getMovieNowPlaying(categoryData: HomeCategoryData) = viewModelScope.launch {
        val result = getMovieNowPlayingUseCase.invoke(currentNowPlayingPage)
        logD("getMovieNowPlaying: $result")
        handleMovieListResponse(result, categoryData)
        currentNowPlayingPage++
    }

    private var currentMovieUpComingPage = 1
    private fun getMovieUpComing(categoryData: HomeCategoryData) = viewModelScope.launch {
        val result = getMovieUpComingUseCase.invoke(currentMovieUpComingPage)
        logD("getMovieUpComing: $result")
        handleMovieListResponse(result, categoryData)
        currentMovieUpComingPage++
    }

    private var currentMoviePopularPage = 1
    private fun getMoviePopular(categoryData: HomeCategoryData) = viewModelScope.launch {
        val result = getMoviePopularUseCase.invoke(currentMoviePopularPage)
        logD("getMoviePopular: $result")
        handleMovieListResponse(result, categoryData)
        currentMoviePopularPage++
    }

    private var currentMovieTopRatePage = 1
    private fun getMovieTopRate(categoryData: HomeCategoryData) = viewModelScope.launch {
        val result = getMovieTopRateUseCase.invoke(currentMovieTopRatePage)
        logD("getMovieTopRate: $result")
        handleMovieListResponse(result, categoryData)
        currentMovieTopRatePage++
    }

    private fun getMovieGenres(categoryData: HomeCategoryData) = viewModelScope.launch {
        when (val result = getMovieGenresUseCase.invoke()) {
            is ResponseHandler.Success -> {
                logD("getMovieGenres: $result")
                categoryData.listItems = result.data.genres as ArrayList<ItemModel>
                _notifyItemChangePosition.value = categoryData.position
            }
            is ResponseHandler.Failure -> {
                categoryData.listItems = null
                _notifyItemChangePosition.value = categoryData.position
            }
            else -> {

            }
        }

    }


    private fun handleMovieListResponse(result: ResponseHandler<MovieResponse>, categoryData: HomeCategoryData) {
        when (result) {
            is ResponseHandler.Success -> {
                categoryData.listItems = result.data.results as ArrayList<ItemModel>
                _notifyItemChangePosition.value = categoryData.position
            }
            is ResponseHandler.Failure -> {
                categoryData.listItems = null
                _notifyItemChangePosition.value = categoryData.position
            }
            else -> {

            }
        }
    }

}