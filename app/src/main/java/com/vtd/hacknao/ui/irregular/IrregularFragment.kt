package com.vtd.hacknao.ui.irregular

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vtd.hacknao.common.base.BaseFragment
import com.vtd.hacknao.databinding.FragmentIrregularBinding
import kotlinx.android.synthetic.main.fragment_irregular.*

class IrregularFragment : BaseFragment<IrregularFragViewModel, FragmentIrregularBinding>(IrregularFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIrregularBinding {
        return FragmentIrregularBinding.inflate(inflater)
    }

    override fun initialize() {
        pdfViewer.fromAsset("pronounce_audio/bqt.pdf").load()
    }
}