package com.app.hack_brain.ui.short_story.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentDetailShortStoryBinding
import kotlinx.android.synthetic.main.fragment_detail_short_story.*

class DetailShortStoryFragment :
    BaseFragment<DetailShortStoryFragViewModel, FragmentDetailShortStoryBinding>(
        DetailShortStoryFragViewModel::class
    ) {

    private val args: DetailShortStoryFragmentArgs by navArgs()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailShortStoryBinding {
        return FragmentDetailShortStoryBinding.inflate(inflater)
    }

    override fun initialize() {
        tvContent.text = args.shortStory.content
    }
}