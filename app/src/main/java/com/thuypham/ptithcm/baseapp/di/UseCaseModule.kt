package com.thuypham.ptithcm.domain.di

import com.thuypham.ptithcm.baseapp.domain.usecase.movie.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetMovieNowPlayingUseCase(get()) }
    factory { GetMoviePopularUseCase(get()) }
    factory { GetMovieTopRateUseCase(get()) }
    factory { GetMovieTrendingUseCase(get()) }
    factory { GetMovieUpComingUseCase(get()) }
    factory { GetMovieGenresUseCase(get()) }
}