package com.example.sampletvfocustest.di

import com.example.sampletvfocustest.providers.*
import org.koin.dsl.module

val appDependenciesModule = module {

    single<SportsDataProvider> { FirebaseSportsDataFactory() }
    single<ImageProvider> { GlideImageFactory() }
    single<StorageDataProvider> { FirebaseStorageFactory() }
}