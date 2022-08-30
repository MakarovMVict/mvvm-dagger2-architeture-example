package com.example.ds_core_android

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import com.example.da_core_android.CommonFragment
import javax.inject.Inject

open class BaseFragment: CommonFragment() {
    @Inject
    internal lateinit var app: Application

    final override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        return inflater//.wrapIfNeeded() use to wrap theme
    }
}

//private fun LayoutInflater.wrapIfNeeded(
//    useRedesignTheme: Boolean
//): LayoutInflater {
//    return if (useRedesignTheme) {
//        val wrappedContext = ContextThemeWrapper(
//            this.context,
//            //theme name
//        )
//        this.cloneInContext(wrappedContext)
//    } else {
//        this
//    }
//}