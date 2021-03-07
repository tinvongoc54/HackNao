package com.app.hack_brain.ui.home

import android.view.LayoutInflater
import com.app.hack_brain.common.base.BaseActivity
import com.app.hack_brain.databinding.ActivityHomeBinding

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(HomeViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(inflater)
    }

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
    }
}
