package com.app.hack_brain.ui.translate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentTranslateBinding

class TranslateFragment : BaseFragment<TranslateFragViewModel, FragmentTranslateBinding>(TranslateFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTranslateBinding {
        return FragmentTranslateBinding.inflate(inflater)
    }

    override fun initialize() {

    }
}