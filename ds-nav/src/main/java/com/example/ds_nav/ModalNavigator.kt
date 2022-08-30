package com.example.ds_nav

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.ArrayDeque

const val ARG_FIRST_ADDED_MODAL = "arg_first_added_modal"

/**
 * Navigator class which handles navigation for navigator name - <modal>
 */
@Navigator.Name("modal")
class ModalNavigator(context: Context, val manager: FragmentManager, val containerId: Int) :
    FragmentNavigator(context, manager, containerId) {

    private val backStack = ArrayDeque<Int>()

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination {
        val destId = destination.id
        val fragment = Class.forName(destination.className).newInstance() as Fragment
        fragment.arguments = args
        if (backStack.isEmpty()) {
            fragment.arguments = args?.apply {
                putBoolean(ARG_FIRST_ADDED_MODAL, true)
            } ?: bundleOf(ARG_FIRST_ADDED_MODAL to true)
        }
        manager.beginTransaction()
            .add(containerId, fragment, generateFragmentTag(destId))
            .addToBackStack(generateBackStackName(backStack.size, destId))
            .commit()

        backStack.add(destId)
        return destination
    }

    override fun popBackStack(): Boolean {
        if (backStack.size == 0) return false

        val destId = backStack.peekLast()!!
        if (manager.isStateSaved) {
            val fragment = manager.findFragmentByTag(generateFragmentTag(destId))
            fragment?.let {
                manager.beginTransaction()
                    .remove(it)
                    .commitAllowingStateLoss()
            }
        } else {
            manager.popBackStack(
                generateBackStackName(backStack.size - 1, destId),
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        backStack.removeLast()
        return true
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String =
        "$backStackIndex-$destId"

    private fun generateFragmentTag(destId: Int): String =
        "dialog-$destId"
}
