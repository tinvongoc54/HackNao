package com.vtd.hacknao.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vtd.hacknao.common.base.BaseRecyclerViewAdapter
import com.vtd.hacknao.data.local.entity.VocabularyEntity
import com.vtd.hacknao.databinding.ItemEverydayVocabularyBinding

class EverydayVocAdapter : BaseRecyclerViewAdapter<VocabularyEntity, EverydayVocAdapter.ItemEverydayVocViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemEverydayVocViewHolder {
        return ItemEverydayVocViewHolder(
            ItemEverydayVocabularyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemEverydayVocViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    inner class ItemEverydayVocViewHolder(private val itemBinding: ItemEverydayVocabularyBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(word: VocabularyEntity) {
            itemBinding.run {
                tvVocabulary.text = word.word
                tvPronounce.text = word.phonetic
                tvMeaning.text = word.shortMean
            }
        }
    }
}