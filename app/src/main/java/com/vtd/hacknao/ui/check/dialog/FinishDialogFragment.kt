package com.vtd.hacknao.ui.check.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vtd.hacknao.R
import com.vtd.hacknao.common.base.BaseDialogFragment
import com.vtd.hacknao.databinding.DialogFinishFragmentBinding

class FinishDialogFragment(
    private val result: Int,
    private val numberQuestion: Int,
    private val onClickNext: () -> Unit,
    private val onClickAgain: () -> Unit
) : BaseDialogFragment<FinishDialogFragViewModel, DialogFinishFragmentBinding>(
    FinishDialogFragViewModel::class
) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogFinishFragmentBinding {
        return DialogFinishFragmentBinding.inflate(inflater)
    }

    override fun initialize() {
        viewBinding.run {
            tvPoint.text = String.format("$result/$numberQuestion")
            tvMessage.text =
                if (result >= (numberQuestion/2)) getString(R.string.text_result_pass) else getString(R.string.text_result_fail)
            btnNext.setOnClickListener {
                onClickNext()
                dismiss()
            }

            btnAgain.setOnClickListener {
                onClickAgain()
                dismiss()
            }
        }
    }
}