package com.app.hack_brain.ui.translate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentTranslateBinding
import com.app.hack_brain.model.uimodel.DetailTranslate

class TranslateFragment : BaseFragment<TranslateFragViewModel, FragmentTranslateBinding>(TranslateFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTranslateBinding {
        return FragmentTranslateBinding.inflate(inflater)
    }

    override fun initialize() {
        viewBinding.ivSearch.setOnClickListener {
            viewModel.doTranslate(viewBinding.tvSearch.text.toString())
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.sentencesList.observe(viewLifecycleOwner, Observer {
            initResultTranslate(it.toMutableList())
        })
    }

    private fun initResultTranslate(list: MutableList<DetailTranslate>) {
        viewBinding.rvResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TranslateAdapter()
            with(adapter as TranslateAdapter) {
                replaceData(list)
            }
        }
    }
}