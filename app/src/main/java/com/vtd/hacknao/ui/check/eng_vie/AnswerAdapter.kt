package com.vtd.hacknao.ui.check.eng_vie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vtd.hacknao.R
import com.vtd.hacknao.common.base.BaseRecyclerViewAdapter
import com.vtd.hacknao.data.local.entity.VocabularyEntity
import com.vtd.hacknao.databinding.ItemAnswerBinding

class AnswerAdapter(
    private val context: Context,
    private val onClickItem: (word: VocabularyEntity, position: Int) -> Unit
) : BaseRecyclerViewAdapter<VocabularyEntity, AnswerAdapter.ItemAnswerViewHolder>() {

    private var isClickableItem = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAnswerViewHolder {
        return ItemAnswerViewHolder(
            ItemAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setIsClickableItem(isClickable: Boolean) {
        isClickableItem = isClickable
    }

    override fun onBindViewHolder(holder: ItemAnswerViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
            holder.onClickItem {
                if (isClickableItem) {
                    onClickItem(it, position)
                    isClickableItem = false
                }
            }
        }
    }

    inner class ItemAnswerViewHolder(private val itemBinding: ItemAnswerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(word: VocabularyEntity) {
            itemBinding.llAnswer.background = ContextCompat.getDrawable(context, R.drawable.background_default_answer)
            itemBinding.tvAnswer.text = word.shortMean
        }

        fun onClickItem(onClick: () -> Unit) {
            itemBinding.root.setOnClickListener { onClick() }
        }
    }
}