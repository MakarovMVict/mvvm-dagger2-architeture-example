package com.example.da_core_android.di

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
abstract class BaseActivityModule {

    @Binds
    @ActivityContext
    abstract fun context(activity: AppCompatActivity): Context

    companion object {

        @Provides
        fun resources(activity: AppCompatActivity): Resources = activity.resources
    }
}

@Module
internal interface ViewModelModule {

//    @Binds
//    @IntoMap
//    @PerActivity
//    @ViewModelKey(ToolbarViewModel::class)
//    fun bindToolbarViewModel(viewModel: ToolbarViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @PerActivity
//    @ViewModelKey(BottomNavigationViewModel::class)
//    fun bindBottomNavigationViewModel(viewModel: BottomNavigationViewModel): ViewModel
}