package com.vtd.hacknao.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vtd.hacknao.common.base.BaseRecyclerViewAdapter
import com.vtd.hacknao.data.local.entity.TargetEntity
import com.vtd.hacknao.databinding.ItemTargetBinding
import com.vtd.hacknao.extension.nullToBlank
import com.vtd.hacknao.extension.showView

class TargetAdapter(
    private val onClickItem: (unit: Int) -> Unit
) : BaseRecyclerViewAdapter<TargetEntity, TargetAdapter.ItemTargetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTargetViewHolder {
        return ItemTargetViewHolder(
            ItemTargetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemTargetViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
            holder.bindClickListener { onClickItem(it.unit ?: 1) }
        }
    }

    inner class ItemTargetViewHolder(private val itemBinding: ItemTargetBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(target: TargetEntity) {
            itemBinding.run {
                tvUnit.text = String.format("Unit %s", target.unit)
                tvTargetDate.text = String.format("Target\n%s", target.date.nullToBlank())
                ivRunning.showView(target.isRunning())
                ivLate.showView(target.isLate())
                ivDone.showView(target.isDone())
            }
        }

        fun bindClickListener(onClick: () -> Unit) {
            itemBinding.root.setOnClickListener { onClick() }
        }
    }
}