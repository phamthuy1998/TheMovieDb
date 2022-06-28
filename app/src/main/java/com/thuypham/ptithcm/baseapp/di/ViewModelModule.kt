package com.thuypham.ptithcm.baseapp.di

import com.thuypham.ptithcm.baseapp.viewmodel.HomeViewModel
import com.thuypham.ptithcm.baseapp.viewmodel.MovieCategoryViewModel
import com.thuypham.ptithcm.baseapp.viewmodel.PeopleViewModel
import com.thuypham.ptithcm.baseapp.viewmodel.PersonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(),) }
    viewModel { MovieCategoryViewModel(get()) }
    viewModel { PeopleViewModel(get()) }
    viewModel { PersonViewModel(get()) }
}