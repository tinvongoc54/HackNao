package com.app.hack_brain.ui.short_story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.databinding.ItemShortStoryBinding
import com.app.hack_brain.model.uimodel.ShortStory

class ShortStoryAdapter(
    private val onItemClicked: (story: ShortStory) -> Unit
) : BaseRecyclerViewAdapter<ShortStory, ShortStoryAdapter.ShortStoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortStoryViewHolder {
        return ShortStoryViewHolder(
            ItemShortStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ShortStoryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
            holder.bindItemClick(it)
        }
    }

    inner class ShortStoryViewHolder(
        private val itemBinding: ItemShortStoryBinding,
        private val onItemClicked: (story: ShortStory) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(story: ShortStory) {
            itemBinding.run {
                tvTitle.text = story.title
            }
        }

        fun bindItemClick(story: ShortStory) {
            itemBinding.run {
                clShortStory.setOnClickListener {
                    onItemClicked(story)
                }
            }
        }
    }
}