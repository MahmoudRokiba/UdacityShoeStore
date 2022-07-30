package com.rokiba.udacityshoestore.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.base.BaseActivity
import com.rokiba.udacityshoestore.databinding.ActivityMainBinding
import com.rokiba.udacityshoestore.extenstion.hideView
import com.rokiba.udacityshoestore.extenstion.openActivity
import com.rokiba.udacityshoestore.extenstion.showView
import com.rokiba.udacityshoestore.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun bindViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun getLayout() = R.layout.activity_main

    override fun init(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setSupportActionBar(dataBinding.toolbar)

        //dataBinding.toolbar.inflateMenu(R.menu.menu_main)
        dataBinding.toolbar.setOnMenuItemClickListener {
            finishAffinity()
            openActivity(SplashActivity::class.java, null, true)
            true
        }
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            try {
                if (menu!= null) {
                    if (destination.id == R.id.listingFragment) {
                        menu.findItem(R.id.actionLogout).isVisible = true
                    } else {
                        menu.findItem(R.id.actionLogout).isVisible = false
                    }
                }
            } catch (e: Exception){
                
            }
        }
    }

    lateinit var menu: Menu

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}