package com.app.hack_brain.ui.check.eng_vie

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentCheckEngVieBinding

class CheckEngVieFragment : BaseFragment<CheckEngVieFragViewModel, FragmentCheckEngVieBinding>(CheckEngVieFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckEngVieBinding {
        return FragmentCheckEngVieBinding.inflate(inflater)
    }

    override fun initialize() {

    }
}