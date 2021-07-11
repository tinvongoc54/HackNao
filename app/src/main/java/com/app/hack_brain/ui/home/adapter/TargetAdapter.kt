package com.app.hack_brain.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.data.local.entity.TargetEntity
import com.app.hack_brain.databinding.ItemTargetBinding
import com.app.hack_brain.extension.convertTimestampToDate
import com.app.hack_brain.extension.nullToBlank
import com.app.hack_brain.extension.showView

class TargetAdapter : BaseRecyclerViewAdapter<TargetEntity, TargetAdapter.ItemTargetViewHolder>() {

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
        getItem(position)?.let { holder.bindData(it) }
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
    }
}