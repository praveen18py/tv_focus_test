package com.example.sampletvfocustest.di

import com.example.sampletvfocustest.providers.FirebaseSportsDataFactory
import com.example.sampletvfocustest.providers.SportsDataProvider
import org.koin.dsl.module

val appDependenciesModule = module {
    single<SportsDataProvider> { FirebaseSportsDataFactory() }
}