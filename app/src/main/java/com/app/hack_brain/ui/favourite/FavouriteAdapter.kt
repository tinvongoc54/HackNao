package com.app.hack_brain.ui.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.databinding.ItemFavouriteBinding
import com.app.hack_brain.extension.nullToBlank

class FavouriteAdapter(
    private val context: Context,
    private var onClickSound: ((word: String) -> Unit)? = null,
    private var onClickFavourite: ((id: Int, position: Int) -> Unit)? = null
) : BaseRecyclerViewAdapter<VocabularyEntity, FavouriteAdapter.ItemViewHolderFavourite>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderFavourite {
        return ItemViewHolderFavourite(
            ItemFavouriteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolderFavourite, position: Int) {
        getItem(position)?.let {
            holder.bindData(context, it)
            holder.bindClickEvent(it, position)
        }
    }

    fun removeListener() {
        onClickFavourite = null
        onClickSound = null
    }

    inner class ItemViewHolderFavourite(private val itemBinding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindData(context: Context, word: VocabularyEntity) {
            itemBinding.run {
                tvWord.text = word.word
                tvMeanings.text = word.shortMean
                tvType.text = word.getTypeOfVoc()
                tvType.setTextColor(ContextCompat.getColor(context, word.getTypeColor(tvType.text.toString())))
            }
        }

        fun bindClickEvent(word: VocabularyEntity, position: Int) {
            itemBinding.run {
                ivFavourite.setOnClickListener { onClickFavourite?.let { it1 -> it1(word.id, position) } }
                ivAudio.setOnClickListener { onClickSound?.let { it1 -> it1(word.word.nullToBlank()) } }
            }
        }
    }
}