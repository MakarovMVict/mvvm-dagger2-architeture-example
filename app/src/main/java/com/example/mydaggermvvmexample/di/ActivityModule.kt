package com.example.mydaggermvvmexample.di

import androidx.appcompat.app.AppCompatActivity
import com.example.mydaggermvvmexample.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        AggregatedActivityModule::class,

    ]
)
interface ActivityModule {

    @Binds
    fun bindsMainActivity(activity: MainActivity): AppCompatActivity
}