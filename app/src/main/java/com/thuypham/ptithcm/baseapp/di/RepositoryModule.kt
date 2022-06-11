package com.thuypham.ptithcm.domain.di

import com.thuypham.ptithcm.baseapp.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { com.thuypham.ptithcm.baseapp.domain.repository.impl.MovieRepositoryImpl(get()) }
}