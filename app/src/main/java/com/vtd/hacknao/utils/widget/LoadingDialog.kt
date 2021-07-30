package com.vtd.hacknao.utils.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vtd.hacknao.R
import com.vtd.hacknao.databinding.DialogLoadingBinding

class LoadingDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return DialogLoadingBinding.inflate(inflater).root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
}
