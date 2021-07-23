package com.app.hack_brain.ui.home

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import com.app.hack_brain.R
import com.app.hack_brain.app.App
import com.app.hack_brain.common.base.BaseActivity
import com.app.hack_brain.databinding.ActivityHomeBinding
import com.app.hack_brain.extension.gone
import com.app.hack_brain.extension.show
import com.app.hack_brain.service.AlarmService
import com.app.hack_brain.ui.short_story.detail.DetailShortStoryFragmentArgs
import com.app.hack_brain.ui.timer.dialog.ChooseTimerFragmentArgs

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(HomeViewModel::class) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var alarmService: AlarmService

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        App.setRunning(true)
    }

    override fun onStop() {
        super.onStop()
        App.setRunning(false)
    }

    override fun initialize() {
        alarmService = AlarmService(this)
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
                        tvPoint.gone()
                        tvTitle.text = getString(R.string.text_checking)
                    }
                    R.id.translateFragment -> {
                        tvTitle.text = getString(R.string.text_translate_sentence)
                    }
                    R.id.irregularFragment -> {
                        tvTitle.text = getString(R.string.text_irregular)
                    }
                    R.id.favouriteFragment -> {
                        tvPoint.gone()
                        tvTitle.text = getString(R.string.text_favourite)
                    }
                    R.id.timerFragment -> {
                        ivAdd.show()
                        tvDone.gone()
                        tvTitle.text = "Hẹn giờ"
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
                        tvPoint.text = "0 điểm"
                        tvTitle.text = "Anh - Việt"
                    }
                    R.id.checkVieEngFragment -> {
                        tvPoint.show()
                        tvPoint.text = "0 điểm"
                        tvTitle.text = "Việt - Anh"
                    }
                    R.id.checkSoundFragment -> {
                        tvPoint.show()
                        tvPoint.text = "0 điểm"
                        tvTitle.text = "Âm thanh"
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
                        R.id.chooseTimerFragment -> {
                            val fragArgs = ChooseTimerFragmentArgs.fromBundle(args)
                            viewBinding.tvTitle.text = if (fragArgs.isOpenApp) "Hẹn giờ mở ứng dụng" else "Hẹn giờ nhắc từ vựng"
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


