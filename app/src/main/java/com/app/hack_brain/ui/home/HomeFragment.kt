package com.app.hack_brain.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeFragViewModel, FragmentHomeBinding>(HomeFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initialize() {

    }
}