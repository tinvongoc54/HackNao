package com.app.hack_brain.ui.home

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseActivity
import com.app.hack_brain.databinding.ActivityHomeBinding

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(HomeViewModel::class) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun initialize() {
        setSupportActionBar(viewBinding.toolbar)
        val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        navHost?.let {
//            navController = it.findNavController()
//            appBarConfiguration = AppBarConfiguration.Builder().build()
//            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
    }
}


