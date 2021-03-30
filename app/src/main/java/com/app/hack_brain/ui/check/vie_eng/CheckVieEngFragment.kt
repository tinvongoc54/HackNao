package com.app.hack_brain.ui.check.vie_eng

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentCheckVieEngBinding

class CheckVieEngFragment : BaseFragment<CheckVieEngFragViewModel, FragmentCheckVieEngBinding>(CheckVieEngFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckVieEngBinding {
        return FragmentCheckVieEngBinding.inflate(inflater)
    }

    override fun initialize() {

    }
}