package com.example.testapp.core

import android.app.Application
import com.example.testapp.di.dataModule
import com.example.testapp.di.domainModule
import com.example.testapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(
                viewModelModule,
                domainModule,
                dataModule,
            )
        }
    }
}