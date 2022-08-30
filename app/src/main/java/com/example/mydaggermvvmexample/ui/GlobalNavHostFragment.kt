package com.example.mydaggermvvmexample.ui

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ds_nav.ModalNavigator

class GlobalNavHostFragment : NavHostFragment() {

    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        navController.navigatorProvider.addNavigator(
            ModalNavigator(
                requireContext(),
                childFragmentManager,
                id
            )
        )
    }

    companion object {
        private const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"
        private const val KEY_START_DESTINATION_ARGS =
            "android-support-nav:fragment:startDestinationArgs"
        fun create(graphResId: Int): GlobalNavHostFragment {
            return create(graphResId, null)
        }

        /**
         * This method the same as [NavHostFragment.create] the only difference is that
         * this method @return [GlobalNavHostFragment]
         */
        private fun create(
            @NavigationRes graphResId: Int,
            startDestinationArgs: Bundle?
        ): GlobalNavHostFragment {
            var b: Bundle? = null
            if (graphResId != 0) {
                b = Bundle()
                b.putInt(KEY_GRAPH_ID, graphResId)
            }
            if (startDestinationArgs != null) {
                if (b == null) {
                    b = Bundle()
                }
                b.putBundle(KEY_START_DESTINATION_ARGS, startDestinationArgs)
            }
            val result = GlobalNavHostFragment()
            if (b != null) {
                result.arguments = b
            }
            return result
        }
    }
}
