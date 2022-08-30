package com.example.base

import android.app.Application
import com.example.base.di.DaggerAppComponent
import com.example.da_core_android.BaseApplication


class MyApplication : BaseApplication() {
    override val appComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun injectApplication(app: Application) {
        appComponent.inject(this)
    }
}