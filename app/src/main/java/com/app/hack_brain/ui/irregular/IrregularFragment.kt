package com.app.hack_brain.ui.irregular

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentIrregularBinding
import kotlinx.android.synthetic.main.fragment_irregular.*

class IrregularFragment : BaseFragment<IrregularFragViewModel, FragmentIrregularBinding>(IrregularFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIrregularBinding {
        return FragmentIrregularBinding.inflate(inflater)
    }

    override fun initialize() {
        pdfViewer.fromAsset("bqt.pdf").load()
    }
}