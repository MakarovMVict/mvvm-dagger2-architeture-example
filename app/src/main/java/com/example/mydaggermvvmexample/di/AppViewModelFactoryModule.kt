package com.example.mydaggermvvmexample.di

import androidx.lifecycle.ViewModelProvider
import com.example.da_core_android.di.AppViewModelFactory
import dagger.Binds
import dagger.Module

/**
* Dagger module that binds provides [AppViewModelFactory]
*/
@Module
abstract class AppViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}