package com.example.sampletvfocustest

import android.app.Application
import com.example.sampletvfocustest.di.appDependenciesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FocusTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@FocusTestApplication)
            modules(listOf(appDependenciesModule))
        }
    }
}