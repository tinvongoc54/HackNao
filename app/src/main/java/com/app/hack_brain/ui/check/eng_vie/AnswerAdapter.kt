package com.app.hack_brain.ui.check.eng_vie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.databinding.ItemAnswerBinding
import com.app.hack_brain.model.uimodel.Word

class AnswerAdapter(
    private val context: Context,
    private val onClickItem: (word: Word, position: Int) -> Unit
) : BaseRecyclerViewAdapter<Word, AnswerAdapter.ItemAnswerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAnswerViewHolder {
        return ItemAnswerViewHolder(
            ItemAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAnswerViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
            holder.onClickItem { onClickItem(it, position) }
        }
    }

    inner class ItemAnswerViewHolder(private val itemBinding: ItemAnswerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(word: Word) {
            itemBinding.llAnswer.background = ContextCompat.getDrawable(context, R.drawable.background_default_answer)
            itemBinding.tvAnswer.text = word.meanings
        }

        fun onClickItem(onClick: () -> Unit) {
            itemBinding.root.setOnClickListener { onClick() }
        }
    }
}