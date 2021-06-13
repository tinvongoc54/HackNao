package com.app.hack_brain.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.databinding.ItemEverydayVocabularyBinding
import com.app.hack_brain.model.uimodel.Word

class EverydayVocAdapter : BaseRecyclerViewAdapter<Word, EverydayVocAdapter.ItemEverydayVocViewHolder>() {

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
        fun bindData(word: Word) {
            itemBinding.run {
                tvVocabulary.text = word.word
                tvPronounce.text = word.phonetic
                tvMeaning.text = word.meanings
            }
        }
    }
}