package com.vtd.hacknao.ui.pronounce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vtd.hacknao.common.base.BaseRecyclerViewAdapter
import com.vtd.hacknao.databinding.ItemPronounceBinding
import com.vtd.hacknao.model.uimodel.Pronounce

class PronounceAdapter :
    BaseRecyclerViewAdapter<Pronounce, PronounceAdapter.ItemPronounceViewHolder>() {

    var onItemClick: ((audio: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPronounceViewHolder {
        return ItemPronounceViewHolder(
            ItemPronounceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemPronounceViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
            holder.bindItemClick {
                onItemClick?.let { click -> click(it.audio) }
            }
        }
    }

    fun removeListener() {
        onItemClick = null
    }

    inner class ItemPronounceViewHolder(private val itemBinding: ItemPronounceBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindData(pronounce: Pronounce) {
            itemBinding.run {
                tvPronounce.text = pronounce.title
                tvMeanings.text = pronounce.content
            }
        }

        fun bindItemClick(onClick: () -> Unit) {
            itemBinding.root.setOnClickListener { onClick() }
        }

    }

}