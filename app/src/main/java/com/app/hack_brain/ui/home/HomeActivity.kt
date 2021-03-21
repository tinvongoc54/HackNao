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
import com.app.hack_brain.extension.gone
import com.app.hack_brain.extension.show
import com.app.hack_brain.ui.short_story.detail.DetailShortStoryFragmentArgs
import timber.log.Timber

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
            navController = it.findNavController()
//            appBarConfiguration = AppBarConfiguration.Builder().build()
//            setupActionBarWithNavController(navController, appBarConfiguration)
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.detailShortStoryFragment -> {
                    viewBinding.ivAudio.show()
                    viewBinding.ivBack.show()
                }
                R.id.shortStoryFragment -> {
                    viewBinding.ivAudio.gone()
                    viewBinding.ivBack.show()
                    viewBinding.tvTitle.text = getString(R.string.text_short_story)
                }
                else -> {
                    viewBinding.ivBack.gone()
                    viewBinding.tvTitle.text = ""
                }
            }

            supportActionBar?.let {
                arguments?.let { args ->
                    when (destination.id) {
                        R.id.detailShortStoryFragment -> {
                            val fragArgs = DetailShortStoryFragmentArgs.fromBundle(args)
                            viewBinding.tvTitle.text = fragArgs.shortStory.title
                            Timber.i("title: %s", fragArgs.shortStory.title)
                        }
                    }
                }
                it.setHomeAsUpIndicator(R.drawable.ic_back)
            }
        }

        viewBinding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}


