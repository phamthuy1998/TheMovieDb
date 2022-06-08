package com.thuypham.ptithcm.domain.di

import com.thuypham.ptithcm.data.repository.MovieRepository
import com.thuypham.ptithcm.domain.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}