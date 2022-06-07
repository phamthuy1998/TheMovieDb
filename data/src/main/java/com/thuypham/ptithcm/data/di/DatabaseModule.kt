package com.thuypham.ptithcm.data.di

import com.thuypham.ptithcm.data.local.AppDatabase
import com.thuypham.ptithcm.data.local.IStorage
import com.thuypham.ptithcm.data.local.SharedPreferencesStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(androidContext()) }
    single<IStorage> { SharedPreferencesStorage(get(), get()) }
}
