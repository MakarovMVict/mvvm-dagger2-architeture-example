package com.example.da_core_android.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.multibindings.Multibinds

@Module //(includes = [DeviceBootCompletedObserversModule::class])
abstract class BaseAppModule {

    @Binds
    @ApplicationContext
    abstract fun context(application: Application): Context

//    @Multibinds
//    @BaseAppInitializers
//    abstract fun baseAppInitializers(): Set<@JvmSuppressWildcards Initializer<Application>>
}