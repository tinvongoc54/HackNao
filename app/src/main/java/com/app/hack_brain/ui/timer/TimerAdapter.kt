package com.app.hack_brain.ui.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.databinding.ItemTimerBinding
import com.app.hack_brain.model.uimodel.Timer

class TimerAdapter(
    private val onClickItem: (timer: Timer) -> Unit,
    private val onClickEditItem: (timer: Timer) -> Unit,
    private val onClickDeleteItem: (position: Int) -> Unit,
    private val onClickCheckBox: (position: Int) -> Unit
) : BaseRecyclerViewAdapter<Timer, TimerAdapter.ItemTimerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTimerViewHolder {
        return ItemTimerViewHolder(
            ItemTimerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemTimerViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
            holder.bindClickItem({
                onClickItem(it)
            }, {
                onClickEditItem(it)
            }, {
                onClickDeleteItem(position)
            }, {
                it.isTurnOn = holder.getCheckBoxSelected()
                onClickCheckBox(position)
            })
        }
    }

    inner class ItemTimerViewHolder(private val itemBinding: ItemTimerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(timer: Timer) {
            itemBinding.run {
                tvTime.text = timer.time
                cbTurnOn.isChecked = timer.isTurnOn
                tvCalendar.text = timer.getStringCalendar()
            }
        }

        fun bindClickItem(
            onClickItem: () -> Unit,
            onClickEditItem: () -> Unit,
            onClickDeleteItem: () -> Unit,
            onClickCheckBox: () -> Unit
        ) {
            itemBinding.run {
                clTimer.setOnClickListener { onClickItem() }
                tvEdit.setOnClickListener { onClickEditItem() }
                tvDelete.setOnClickListener { onClickDeleteItem() }
                cbTurnOn.setOnClickListener { onClickCheckBox() }
            }
        }

        fun getCheckBoxSelected(): Boolean {
            return itemBinding.cbTurnOn.isChecked
        }
    }
}