package com.example.da_core_android.di

import androidx.fragment.app.Fragment
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity

/**
 * Scope which makes provided by Dagger dependencies life time the same as [Fragment] has.
 * While the [Fragment]'s instance is alive the dependencies marked with this scope will be alive.
 *
 * Example usage:
 *
 *  @Module
 *  interface MyFragmentModule {
 *      @ContributesAndroidInjector(modules = [MyUserInteractionModule::class])
 *      @PerFragment
 *      fun contributeMyFragment(): MyFragment
 *  }
 *
 *
 *  @Module
 *  internal class MyUserInteractionModule {
 *      @Provides @PerFragment
 *      fun provideMyUserInteraction() = MyUserInteraction()
 *  }
 *
 *
 *  class MyFragment : BaseFragment() {
 *      @Inject lateinit var myUserInteraction: myUserInteraction
 *  }
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerFragment

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerService