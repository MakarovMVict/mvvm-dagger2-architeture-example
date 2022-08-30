package com.example.home_ui.di

import com.example.home_ui.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {

    @ContributesAndroidInjector
    abstract fun providesHomeFragment(): HomeFragment
}
