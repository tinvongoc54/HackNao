package com.app.hack_brain.ui.short_story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentShortStoryBinding
import com.app.hack_brain.extension.navigateWithSlideAnim
import com.app.hack_brain.model.uimodel.ShortStory
import com.google.gson.Gson

class ShortStoryFragment :
    BaseFragment<ShortStoryFragViewModel, FragmentShortStoryBinding>(ShortStoryFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShortStoryBinding {
        return FragmentShortStoryBinding.inflate(inflater)
    }

    override fun initialize() {
        initShortStoryAdapter()
    }

    private fun navigateToDetailShortStory(story: ShortStory) {
        val action = ShortStoryFragmentDirections.actionShortStoryFragmentToDetailFragment(story)
        navigateWithSlideAnim(action)
    }

    private fun getShortStoryList(): List<ShortStory> {
        var json = ""
        try {
            val activity = activity ?: return emptyList()
            val inputStream = activity.assets.open("short_story")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            json = String(buffer)
        } catch (ex: Exception) {

        }
        return Gson().fromJson(json, Array<ShortStory>::class.java).asList()
    }

    private fun initShortStoryAdapter() {
        viewBinding.run {
            rvShortStoryList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ShortStoryAdapter {
                    navigateToDetailShortStory(it)
                }
                with(adapter as ShortStoryAdapter) {
                    replaceData(getShortStoryList().toMutableList())
                }
            }
        }
    }

}