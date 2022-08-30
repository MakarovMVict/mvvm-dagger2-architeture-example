package com.example.mydaggermvvmexample.di

import com.example.da_core_android.di.BaseAppModule
import com.example.da_core_android.di.PerActivity
import com.example.mydaggermvvmexample.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module(
    includes = [//here add data dependencies
        BaseAppModule::class
    ]
)
interface AppModule {

    @ContributesAndroidInjector(
        modules = [
            ActivityModule::class,
            //ScreenInfoObserverModule::class
        ]
    )
    @PerActivity
    fun mainActivityInjector(): MainActivity
    //here can add locationModule and etc
}