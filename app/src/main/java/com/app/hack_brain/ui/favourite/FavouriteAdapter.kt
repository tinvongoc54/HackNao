package com.app.hack_brain.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.databinding.ItemFavouriteBinding
import com.app.hack_brain.model.uimodel.Word

class FavouriteAdapter(
    private var onClickSound: ((word: Word) -> Unit)? = null,
    private var onClickFavourite: ((word: Word) -> Unit)? = null
) : BaseRecyclerViewAdapter<Word, FavouriteAdapter.ItemViewHolderFavourite>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderFavourite {
        return ItemViewHolderFavourite(
            ItemFavouriteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolderFavourite, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
            holder.bindClickEvent(it)
        }
    }

    fun removeListener() {
        onClickFavourite = null
        onClickSound = null
    }

    inner class ItemViewHolderFavourite(private val itemBinding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindData(word: Word) {
            itemBinding.run {
                tvWord.text = word.word
                tvMeanings.text = word.meanings
            }
        }

        fun bindClickEvent(word: Word) {
            itemBinding.run {
                ivFavourite.setOnClickListener { onClickFavourite?.let { it1 -> it1(word) } }
                ivAudio.setOnClickListener { onClickSound?.let { it1 -> it1(word) } }
            }
        }
    }
}