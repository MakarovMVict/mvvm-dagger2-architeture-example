/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mydaggermvvmexample.ui

import android.util.SparseArray
import androidx.core.util.forEach
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.da_core_android.CommonFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private var NAV_GRAPH_IDS: List<Int>? = null

/**
 * Manages the various graphs needed for a [BottomNavigationView].
 *
 * This sample is a workaround until the Navigation Component supports multiple back stacks.
 */
internal fun BottomNavigationView.setupWithNavController(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    selectionListener: (menuId: Int) -> Unit
): LiveData<NavController> {
    NAV_GRAPH_IDS = navGraphIds
    // Map of tags
    val graphIdToTagMap = SparseArray<String>()
    // Result. Mutable live data with the selected controlled
    val selectedNavController = MutableLiveData<NavController>()

    // First create a NavHostFragment for each NavGraph ID
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        // Obtain its id
        val graphId = navHostFragment.navController.graph.id

        // Save to the map
        graphIdToTagMap.put(graphId, fragmentTag)

        // Attach or detach nav host fragment depending on whether it's the selected item.
        if (this.selectedItemId == graphId) {
            // Update livedata with the selected graph
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    // Now connect selecting an item with swapping Fragments
    var selectedItemTag = graphIdToTagMap[this.selectedItemId]

    // When a navigation item is selected
    setOnNavigationItemSelectedListener { item ->
        selectionListener.invoke(item.itemId)
        // Don't do anything if the state is state has already been saved.
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            if (selectedItemTag != newlySelectedItemTag) {
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                    as NavHostFragment

                val previousNavHostFragment = fragmentManager.findFragmentByTag(selectedItemTag)
                    as NavHostFragment
                val previousFragment =
                    previousNavHostFragment.childFragmentManager.primaryNavigationFragment
                if (previousFragment is CommonFragment) {
                    previousFragment.onFragmentDetached()
                }

                // Commit a transaction that cleans the back stack
                fragmentManager.beginTransaction()
                    .setCustomAnimations(
                        androidx.navigation.ui.R.anim.nav_default_enter_anim,
                        androidx.navigation.ui.R.anim.nav_default_exit_anim,
                        androidx.navigation.ui.R.anim.nav_default_pop_enter_anim,
                        androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                    )
                    .attach(selectedFragment)
                    .setPrimaryNavigationFragment(selectedFragment)
                    .apply {
                        // Detach all other Fragments
                        graphIdToTagMap.forEach { _, fragmentTagIter ->
                            if (fragmentTagIter != newlySelectedItemTag) {
                                detach(fragmentManager.findFragmentByTag(fragmentTagIter)!!)
                            }
                        }
                    }
                    .setReorderingAllowed(true)
                    .commit()

                selectedItemTag = newlySelectedItemTag
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    // Handle deep link
    // setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    return selectedNavController
}

//internal fun BottomNavigationView.switchTabAndLaunchDeepLink(
//    intent: Intent?,
//    fragmentManager: FragmentManager,
//    containerId: Int,
//    launchTab: TabMatcher,
//    navControllerMethod: MethodOption,
//    popToRootTabFragment: Boolean
//): Boolean {
//    val currentNavGraphId = this.selectedItemId
//
//    intent?.let {
//        intent.data?.let { uri ->
//            val launchIndex = launchTab.toIndex()
//            val navController = NAV_GRAPH_IDS
//                ?.mapIndexed { index, navGraphId ->
//                    getTabNavController(index, fragmentManager, navGraphId, containerId)
//                }
//                ?.filterIndexed { index, navController ->
//                    chooseNavControllerWhichCanHandleDeepLink(
//                        launchIndex, navController, currentNavGraphId, index, uri
//                    )
//                }
//                ?.firstOrNull()
//            navController?.let {
//                if (currentNavGraphId != it.graph.id) {
//                    changeTabAndNavigateOrHandleDeepLink(
//                        uri, it, intent, navControllerMethod, popToRootTabFragment
//                    )
//                } else {
//                    navigateOrHandleDeepLinkByNavController(
//                        uri, it, intent, navControllerMethod, popToRootTabFragment
//                    )
//                }
//                return true
//            }
//            if (launchTab is CURRENT) {
//                return switchTabAndLaunchDeepLink(
//                    intent,
//                    fragmentManager,
//                    containerId,
//                    launchTab.orElse,
//                    navControllerMethod,
//                    popToRootTabFragment
//                )
//            }
//        }
//    }
//    return false
//}

//private fun BottomNavigationView.changeTabAndNavigateOrHandleDeepLink(
//    uri: Uri,
//    it: NavController,
//    intent: Intent?,
//    navControllerMethod: MethodOption,
//    popToRootTabFragment: Boolean
//) {
//    this.selectedItemId = it.graph.id
//    Handler().post {
//        navigateOrHandleDeepLinkByNavController(
//            uri, it, intent, navControllerMethod, popToRootTabFragment
//        )
//    }
//}

//private fun chooseNavControllerWhichCanHandleDeepLink(
//    launchIndex: Int,
//    navController: NavController,
//    currentNavGraphId: Int,
//    index: Int,
//    uri: Uri
//): Boolean {
//    return (
//        (
//            (launchIndex == -1 && (navController.graph.id == currentNavGraphId)) ||
//                launchIndex == index
//            ) &&
//            navController.graph.hasDeepLink(uri)
//        )
//}

private fun getTabNavController(
    index: Int,
    fragmentManager: FragmentManager,
    navGraphId: Int,
    containerId: Int
): NavController {
    val fragmentTag = getFragmentTag(index)

    // Find or create the Navigation host fragment
    val navHostFragment = obtainNavHostFragment(
        fragmentManager,
        fragmentTag,
        navGraphId,
        containerId
    )
    return navHostFragment.navController
}

//private fun navigateOrHandleDeepLinkByNavController(
//    uri: Uri,
//    it: NavController,
//    intent: Intent?,
//    navControllerMethod: MethodOption,
//    popToRootTabFragment: Boolean
//) {
//    if (popToRootTabFragment) {
//        it.popBackStack(it.graph.startDestination, false)
//        it.navigate(it.graph.startDestination)
//    } else {
//        when (navControllerMethod) {
//            HandleUrl -> it.handleDeepLink(intent)
//            is Navigate -> it.navigate(uri, navControllerMethod.navOptions)
//            is Default ->
//                if (uri.hasMacysScheme()) {
//                    it.navigate(uri, navControllerMethod.navOptions)
//                } else {
//                    it.handleDeepLink(intent)
//                }
//        }
//    }
//}

//private fun TabMatcher.toIndex(): Int {
//    return when (this) {
//        HOME -> 0
//        SHOP -> 1
//        FAVORITES -> 2
//        BAG -> 3
//        ACCOUNT -> 4
//        else -> -1
//    }
//}

sealed class MethodOption {
    data class Navigate(val navOptions: NavOptions? = null) : MethodOption()
    object HandleUrl : MethodOption()
    data class Default(val navOptions: NavOptions? = null) : MethodOption()
}

private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: SparseArray<String>,
    fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
            as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(
            navController.graph.startDestination, false
        )
        navController.navigate(navController.graph.id)
    }
}

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.beginTransaction()
        .detach(navHostFragment)
        .commitNow()
}

private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.beginTransaction()
        .attach(navHostFragment)
        .apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(navHostFragment)
            }
        }
        .commitNow()
}

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    val navHostFragment = GlobalNavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
        .add(containerId, navHostFragment, fragmentTag)
        .commitNow()
    return navHostFragment
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"
