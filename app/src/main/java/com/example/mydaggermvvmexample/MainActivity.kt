package com.example.mydaggermvvmexample

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.da_core_android.BaseActivity
import com.example.da_core_android.domain.NavigationTab
import com.example.ds_core_android.NavigationTab
import com.example.mydaggermvvmexample.databinding.ActivityMainBinding
import com.example.mydaggermvvmexample.ui.BottomNavTabsStreamImpl
import com.example.mydaggermvvmexample.ui.MethodOption
import com.example.mydaggermvvmexample.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import javax.inject.Inject

/**
 * inspired by article
 * https://medium.com/@shashankmohabia/dagger-android-with-mvvm-dependency-injection-for-android-3a7e33ad1013
 */

class MainActivity : BaseActivity(),
com.example.ds_core_android.BaseActivity{

    @Inject
    internal lateinit var bottomNavTabsStream: BottomNavTabsStreamImpl

    private lateinit var binding: ActivityMainBinding

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            val launchIntent = intent
            launchIntent?.data?.let {
                // The intent should be cleared to avoid wrong navigation in architecture component,
                // it takes intent under the hood, so there is only one way
                intent = Intent()
            }
            setupBottomNavigationBar()
            launchIntent.dataString?.let {
                handleActionView(launchIntent, MethodOption.Navigate())
            }
        }
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        setUpBottomNavBar()
        // Disable icon tint, as it is handled in the drawables themselves
        binding.bottomNavigation.itemIconTintList = null
        val bottomNavigationView = binding.bottomNavigation

        val navGraphIds = listOf(
            R.navigation.nav_graph_home_tab,
            //TODO add other graphs according to menu buttons
        )

        val topLevelDestinations = setOf(
//            notificationsplash.nav.R.id.destination_notification_splash_get_started
//            - here for first page
            com.example.home_nav.R.id.destination_home //TODO add top dest paths
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            selectionListener = this::tabStreamPoster
        )

        // Whenever the selected controller changes, setup the action bar.
        val appBarConfig = AppBarConfiguration(topLevelDestinations)

        controller.observe(
            this,
            Observer { navController ->
                setupActionBarWithNavController(navController, appBarConfig)
                reAddDestinationChangeListener(navController)
            }
        )
        currentNavController = controller

        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            onNavItemReselected(controller.value!!, topLevelDestinations)
        }
    }
    private fun tabStreamPoster(menuId: Int) {
        when (menuId) {
            R.id.nav_graph_home_tab -> {
                bottomNavTabsStream.post(NavigationTab.HOME)
            }
            R.id.nav_graph_shop_tab -> {
                bottomNavTabsStream.post(NavigationTab.SHOP)
            }
//            R.id.nav_graph_bag_tab -> {
//                bottomNavTabsStream.post(NavigationTab.BAG)
//            }
//            R.id.nav_graph_favorites_tab -> {
//                bottomNavTabsStream.post(NavigationTab.FAVORITES)
//            }
//            R.id.nav_graph_account_tab -> {
//                bottomNavTabsStream.post(NavigationTab.ACCOUNT)
//            }
        }
    }

    override fun getCoordinatorLayout(): CoordinatorLayout? {
        return findViewById(com.example.home_ui.R.id.container_coordinator)
    }

    private fun handleActionView(
        intent: Intent?,
        navControllerMethod: MethodOption
    ) {
        intent?.let {
            if (Intent.ACTION_VIEW == intent.action) {
                intent.data?.let { uriInput ->
//                    handleUri(uriInput, navControllerMethod)
                }
//                    ?: logger.e(
//                    "ActionView does not have uri, intent.data = ${intent.data}"
//                )
            }
        }
    }

    private fun setUpBottomNavBar() {
//        binding.bottomNavigation.itemTextAppearanceActive =
//            adp.ds.ui.R.style.BottomNavigation_TextAppearance_Redesign_Active
//        binding.bottomNavigation.itemTextAppearanceInactive =
//            adp.ds.ui.R.style.BottomNavigation_TextAppearance_Redesign_InActive
        setCustomIconSize(iconPos = 1, width = 32f, height = 24f)
//        observeProfileChanges()
    }
    private fun setCustomIconSize(iconPos: Int, width: Float, height: Float) {
        val menuView = binding.bottomNavigation.getChildAt(0) as BottomNavigationMenuView
        for (i in 0 until menuView.childCount) {
            if (i == iconPos) {
                val iconView = menuView.getChildAt(i)
                    .findViewById<View>(com.google.android.material.R.id.icon)
                val layoutParams = iconView.layoutParams
                val displayMetrics = resources.displayMetrics
                layoutParams.width = TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, displayMetrics).toInt()
                layoutParams.height = TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, displayMetrics).toInt()
                iconView.layoutParams = layoutParams
            }
        }
    }
//    private fun observeProfileChanges() {
//        profileDisposable.dispose()
//        profileDisposable = lastKnownUserProfileStream.stream()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                val firstName = it.getOrNull().getFirstNameOrEmpty()
//                if (firstName.length <= 9 && firstName.isNotEmpty()) {
//                    binding.bottomNavigation.menu
//                        .findItem(R.id.nav_graph_account_tab).title = firstName
//                } else {
//                    binding.bottomNavigation.menu
//                        .findItem(R.id.nav_graph_account_tab).title =
//                        getString(R.string.title_account)
//                }
//            }
//        profileDisposable.let { compositeDisposable += it }
//    }
    override fun onPostResume() {
        super.onPostResume()
    }
}