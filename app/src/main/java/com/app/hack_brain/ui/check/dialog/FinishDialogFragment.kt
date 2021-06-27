package com.app.hack_brain.ui.check.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.hack_brain.R
import com.app.hack_brain.common.Constant
import com.app.hack_brain.common.base.BaseDialogFragment
import com.app.hack_brain.databinding.DialogFinishFragmentBinding

class FinishDialogFragment(
    private val result: Int,
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
            tvPoint.text = String.format("$result/20")
            tvMessage.text =
                if (result > (Constant.AMOUNT_VOC_AN_UNIT/2)) getString(R.string.text_result_pass) else getString(R.string.text_result_fail)
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