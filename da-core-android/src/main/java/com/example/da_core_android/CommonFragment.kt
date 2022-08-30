package com.example.da_core_android

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class CommonFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun androidInjector(): AndroidInjector<Any> = childFragmentInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    /**
     * Called when a user navigates from this fragment.
     */
    open fun onFragmentDetached() {
    }


}