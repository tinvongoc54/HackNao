package com.vtd.hacknao.ui.short_story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vtd.hacknao.common.base.BaseRecyclerViewAdapter
import com.vtd.hacknao.databinding.ItemShortStoryBinding
import com.vtd.hacknao.model.uimodel.ShortStory

class ShortStoryAdapter(
    private var onItemClicked: ((story: ShortStory) -> Unit)? = null
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

    fun removeListener() {
        onItemClicked = null
    }

    inner class ShortStoryViewHolder(
        private val itemBinding: ItemShortStoryBinding,
        private val onItemClicked: ((story: ShortStory) -> Unit)? = null
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(story: ShortStory) {
            itemBinding.run {
                tvTitle.text = story.title
            }
        }

        fun bindItemClick(story: ShortStory) {
            itemBinding.run {
                clShortStory.setOnClickListener {
                    onItemClicked?.invoke(story)
                }
            }
        }
    }
}