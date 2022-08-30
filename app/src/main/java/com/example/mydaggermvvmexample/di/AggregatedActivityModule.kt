package com.example.mydaggermvvmexample.di

import com.example.da_core_android.di.BaseActivityModule
import com.example.home_ui.HomeFragment
import com.example.home_ui.di.HomeFragmentModule
import dagger.Module


@Module(
    includes = [//here add ui dependencies
        BaseActivityModule::class,
        HomeFragmentModule::class
    ]
)
interface AggregatedActivityModule