package com.vtd.hacknao.ui.check.vie_eng

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vtd.hacknao.common.base.BaseRecyclerViewAdapter
import com.vtd.hacknao.databinding.ItemCharacterBinding
import timber.log.Timber

class CharacterAdapter : BaseRecyclerViewAdapter<Char, CharacterAdapter.ItemCharacterViewHolder>() {

    private var positionShow = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCharacterViewHolder {
        return ItemCharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemCharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it, position)
        }
    }

    fun setPositionShowCharacter(position: Int) {
        positionShow = position
    }

    inner class ItemCharacterViewHolder(private val itemBinding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(char: Char, position: Int) {
            Timber.i("pos: $position, show: $positionShow")
            itemBinding.tvCharacter.text = if (position == positionShow) char.toString() else ""
        }
    }
}