package com.app.hack_brain.ui.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentTimerBinding

class TimerFragment : BaseFragment<TimerFragViewModel, FragmentTimerBinding>(TimerFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimerBinding {
        return FragmentTimerBinding.inflate(inflater)
    }

    override fun initialize() {

    }
}