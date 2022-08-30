package com.example.da_core_android

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.da_core_android.di.BaseAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseApplication : Application(), LifecycleObserver, HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

//    @Inject
//    @BaseAppInitializers
//    lateinit var baseAppInitializers: Set<@JvmSuppressWildcards Initializer<Application>>

    abstract val appComponent: BaseAppComponent

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        injectApplication(this)
//        baseAppInitializers.sortedByDescending { it.priority }.forEach { it.initialize(this) }
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    abstract fun injectApplication(app: Application)
}