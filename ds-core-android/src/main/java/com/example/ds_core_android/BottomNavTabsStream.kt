package com.example.ds_core_android

import com.example.da_core.stream.Stream
import io.reactivex.Observable
import io.reactivex.Single

interface BottomNavTabsStream : Stream<NavigationTab> {

    override fun stream(): Observable<NavigationTab>

    override fun single(): Single<NavigationTab>

    override fun value(): NavigationTab
}

enum class NavigationTab {
    HOME,
    SHOP
}