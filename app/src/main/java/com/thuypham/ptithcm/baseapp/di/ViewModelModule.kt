package com.thuypham.ptithcm.baseapp.di

import com.thuypham.ptithcm.baseapp.viewmodel.HomeViewModel
import com.thuypham.ptithcm.baseapp.viewmodel.MovieCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(),) }
    viewModel { MovieCategoryViewModel(get()) }
}