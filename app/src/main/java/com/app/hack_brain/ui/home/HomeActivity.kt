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
import com.app.hack_brain.ui.check.eng_vie.CheckEngVieFragmentArgs
import com.app.hack_brain.ui.check.vie_eng.CheckVieEngFragmentArgs
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
            viewBinding.run {
                ivBack.show()
                when (destination.id) {
                    R.id.detailShortStoryFragment -> {
                        ivPause.show()
                    }
                    R.id.shortStoryFragment -> {
                        ivPlay.gone()
                        ivPause.gone()
                        tvTitle.text = getString(R.string.text_short_story)
                    }
                    R.id.checkFragment -> {
                        tvTitle.text = getString(R.string.text_checking)
                    }
                    R.id.translateFragment -> {
                        tvTitle.text = getString(R.string.text_translate_sentence)
                    }
                    R.id.irregularFragment -> {
                        tvTitle.text = getString(R.string.text_irregular)
                    }
                    R.id.favouriteFragment -> {
                        tvTitle.text = getString(R.string.text_favourite)
                    }
                    R.id.timerFragment -> {
                        ivAdd.show()
                        tvDone.gone()
                        tvTitle.text = getString(R.string.text_timer)
                    }
                    R.id.chooseTimerFragment -> {
                        ivAdd.gone()
                        ivBack.show()
                        tvDone.show()
                    }
                    R.id.pronounceFragment -> {
                        tvTitle.text = getString(R.string.text_pronounce)
                    }
                    R.id.checkEngVieFragment -> {
                        tvPoint.show()
                        tvTitle.text = "Anh - Việt"
                    }
                    R.id.checkVieEngFragment -> {
                        tvPoint.show()
                        tvTitle.text = "Việt - Anh"
                    }
                    else -> {
                        ivBack.gone()
                        ivAdd.gone()
                        tvDone.gone()
                        tvPoint.gone()
                        tvTitle.text = ""
                    }
                }
            }

            supportActionBar?.let {
                arguments?.let { args ->
                    when (destination.id) {
                        R.id.detailShortStoryFragment -> {
                            val fragArgs = DetailShortStoryFragmentArgs.fromBundle(args)
                            viewBinding.tvTitle.text = fragArgs.shortStory.title
                        }
                        else -> {

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

    fun setPoint(point: Int) {
        viewBinding.tvPoint.text = String.format("$point điểm")
    }
}


