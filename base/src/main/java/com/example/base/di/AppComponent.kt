package com.example.base.di

import android.app.Application
import com.example.base.MyApplication
import com.example.da_core_android.di.BaseAppComponent
import com.example.mydaggermvvmexample.di.ActivityModule
import com.example.mydaggermvvmexample.di.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

//import dagger.android.support.AndroidSupportInjectionModule
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        //AndroidSupportInjectionModule::class,
        AppModule::class,
        ApplicationContextModule::class
    ]
)
interface AppComponent:BaseAppComponent  {

    // Add bindings to share with dependent components here*

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        // Add any component/module bindings you want to explicitly provide here*

        fun build(): AppComponent
    }
//@Component.Factory
//interface Factory {
//    fun create(@BindsInstance application: Application): AppComponent
//}

    fun inject(app: MyApplication)
}