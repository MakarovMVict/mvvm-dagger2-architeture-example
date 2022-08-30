package com.example.da_core_android.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext

//@Qualifier
//@Retention(AnnotationRetention.RUNTIME)
//annotation class BaseAppInitializers

///**
// * [BootCompletedReceiver] observers can be provided using this annotation
// *
// *   E.g.
// *
// *   @Binds
// *   @Singleton
// *   @IntoSet
// *   @DeviceBootCompletedObserver
// *   fun provideOnBootGeofenceCleanerRunnable(
// *       runnable: OnBootGeofenceCleaner
// *   ): Runnable
// */
//@Qualifier
//@Retention(AnnotationRetention.RUNTIME)
//annotation class DeviceBootCompletedObserver
//
//@Qualifier
//@Retention(AnnotationRetention.RUNTIME)
//annotation class GoogleMapsKey