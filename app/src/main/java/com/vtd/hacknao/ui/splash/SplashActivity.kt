package com.vtd.hacknao.ui.splash

import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.vtd.hacknao.common.base.BaseActivity
import com.vtd.hacknao.databinding.ActivitySplashBinding
import com.vtd.hacknao.ui.home.HomeActivity

class SplashActivity : BaseActivity<SplashActViewModel, ActivitySplashBinding>(SplashActViewModel::class) {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }

    override fun initialize() {
        viewModel.delay()
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.delaySplash.observe(this, Observer {
            startActivity(
                Intent(
                    this@SplashActivity,
                    HomeActivity::class.java
                )
            )
            finish()
        })
    }
}