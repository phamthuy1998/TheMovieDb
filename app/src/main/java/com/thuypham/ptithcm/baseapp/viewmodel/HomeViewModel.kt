package com.thuypham.ptithcm.baseapp.viewmodel

import com.thuypham.ptithcm.baselib.base.base.BaseViewModel
import com.thuypham.ptithcm.domain.usecase.movie.*

class HomeViewModel(
    private val getMovieGenres: GetMovieGenresUseCase,
    private val getMovieTrendingUseCase: GetMovieTrendingUseCase,
    private val getMovieNowPlayingUseCase: GetMovieNowPlayingUseCase,
    private val getMovieUpComingUseCase: GetMovieUpComingUseCase,
    private val getMoviePopularUseCase: GetMoviePopularUseCase,
    private val getMovieTopRateUseCase: GetMovieTopRateUseCase,
) : BaseViewModel() {


}