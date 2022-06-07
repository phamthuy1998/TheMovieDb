package com.thuypham.ptithcm.baseapp

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.thuypham.ptithcm.data.di.databaseModule
import com.thuypham.ptithcm.baseapp.di.viewModelModule
import com.thuypham.ptithcm.data.di.networkModule
import com.thuypham.ptithcm.domain.di.repositoryModule
import com.thuypham.ptithcm.domain.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
        fun applicationContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        initKoin()
        super.onCreate()

    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule,
                useCaseModule,
                networkModule
            )
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}