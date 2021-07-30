package com.vtd.hacknao.ui.timer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vtd.hacknao.R
import com.vtd.hacknao.common.base.BaseRecyclerViewAdapter
import com.vtd.hacknao.data.local.entity.TimerEntity
import com.vtd.hacknao.databinding.ItemTimerBinding
import com.vtd.hacknao.extension.gone

class TimerAdapter(
    private val context: Context,
    private val isOpenApp: Boolean,
    private val onClickEditItem: (timer: TimerEntity) -> Unit,
    private val onClickDeleteItem: (timer: TimerEntity, position: Int) -> Unit,
    private val onClickCheckBox: (timer: TimerEntity) -> Unit
) : BaseRecyclerViewAdapter<TimerEntity, TimerAdapter.ItemTimerViewHolder>() {

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
            holder.bindData(context, it, isOpenApp, isHideDelete = position == 0)
            holder.bindClickItem({
                onClickEditItem(it)
            }, {
                onClickDeleteItem(it, position)
            }, {
                it.isTurnOn = holder.getCheckBoxSelected()
                onClickCheckBox(it)
            })
        }
    }

    inner class ItemTimerViewHolder(private val itemBinding: ItemTimerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(
            context: Context,
            timer: TimerEntity,
            isOpenApp: Boolean,
            isHideDelete: Boolean
        ) {
            itemBinding.run {
                tvTime.text = timer.getHour()
                cbTurnOn.isChecked = timer.isTurnOn
                tvCalendar.text = timer.getStringCalendar()
                llSwipe.layoutParams.width =
                    context.resources.getDimension(if (isHideDelete) R.dimen.dp_60 else R.dimen.dp_120)
                        .toInt()
                llSwipe.requestLayout()
                tvVocabulary.gone(isOpenApp)
                tvVocabulary.text = if (isOpenApp.not()) timer.getListVocabulary() else ""
            }
        }

        fun bindClickItem(
            onClickEditItem: () -> Unit,
            onClickDeleteItem: () -> Unit,
            onClickCheckBox: () -> Unit
        ) {
            itemBinding.run {
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