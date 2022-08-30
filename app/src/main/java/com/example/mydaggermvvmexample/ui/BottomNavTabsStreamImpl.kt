package com.example.mydaggermvvmexample.ui


import com.example.ds_core_android.BottomNavTabsStream
import com.example.ds_core_android.NavigationTab
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BottomNavTabsStreamImpl @Inject constructor() : BottomNavTabsStream {

    private val actualSubject = BehaviorSubject.createDefault(DEFAULT_STATUS)
    private val serializedSubject = actualSubject.toSerialized()

    fun post(newTab: NavigationTab) = serializedSubject.onNext(newTab)

    override fun stream(): Observable<NavigationTab> = serializedSubject.hide()

    override fun single(): Single<NavigationTab> = Single.just(value())

    override fun value(): NavigationTab = actualSubject.value ?: DEFAULT_STATUS

    companion object {
        private val DEFAULT_STATUS: NavigationTab =
            NavigationTab.EMPTY
    }
}