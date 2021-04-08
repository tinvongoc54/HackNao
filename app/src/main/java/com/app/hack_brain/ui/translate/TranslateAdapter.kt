package com.app.hack_brain.ui.translate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.databinding.ItemTranslateBinding
import com.app.hack_brain.model.uimodel.DetailTranslate

class TranslateAdapter :
    BaseRecyclerViewAdapter<DetailTranslate, TranslateAdapter.ItemTranslateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTranslateViewHolder {
        return ItemTranslateViewHolder(
            ItemTranslateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemTranslateViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    inner class ItemTranslateViewHolder(private val itemBinding: ItemTranslateBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(item: DetailTranslate) {
            itemBinding.run {
                tvSentence.text = HtmlCompat.fromHtml(getTextHighlight(item.en), HtmlCompat.FROM_HTML_MODE_LEGACY)
                tvTranslate.text = item.vi
            }
        }

        private fun getTextHighlight(text: String): String {
            return text.replace("<em>", "<font color='#343C6A'>").replace("</em>", "</font>")
        }
    }
}