package com.app.hack_brain.utils.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import com.app.hack_brain.common.base.BaseSimpleDialogFragment
import com.app.hack_brain.databinding.DialogMessageBinding
import com.app.hack_brain.extension.show

class CustomDialogFragment(
    private val title: String?,
    private val content: String?,
    private val onConfirm: (() -> Unit)?
) : BaseSimpleDialogFragment<DialogMessageBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogMessageBinding {
        return DialogMessageBinding.inflate(inflater)
    }

    override fun initialize() {
        title?.let {
            viewBinding.tvTitle.show()
            viewBinding.tvTitle.text = title
        }

        content?.let {
            viewBinding.tvContent.text = content
        }

        viewBinding.btnOk.setOnClickListener {
            onConfirm?.invoke()
            dialog?.dismiss()
        }

        viewBinding.root.clipToOutline = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        with(dialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
        }
        return dialog
    }

    data class Builder(
        private val title: String? = null,
        private var content: String? = null,
        private var onConfirm: (() -> Unit)? = null
    ) {
        fun build() =
            CustomDialogFragment(title, content, onConfirm)
    }
}