package com.thuypham.ptithcm.domain.di

import com.thuypham.ptithcm.domain.repository.MovieRepository
import com.thuypham.ptithcm.domain.repository.PeopleRepository
import com.thuypham.ptithcm.domain.repository.impl.MovieRepositoryImpl
import com.thuypham.ptithcm.domain.repository.impl.PeopleRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single<PeopleRepository> { PeopleRepositoryImpl(get()) }
}