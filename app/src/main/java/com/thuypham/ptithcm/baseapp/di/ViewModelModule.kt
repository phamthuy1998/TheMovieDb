package com.thuypham.ptithcm.baseapp.di

import com.thuypham.ptithcm.baseapp.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(),) }
    viewModel { MovieCategoryViewModel(get(), get()) }
    viewModel { PeopleViewModel(get()) }
    viewModel { PersonViewModel(get()) }
    viewModel { GenreViewModel(get()) }
    viewModel { MovieViewModel(get()) }
}