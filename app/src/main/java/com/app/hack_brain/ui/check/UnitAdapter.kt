package com.app.hack_brain.ui.check

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.databinding.ItemCheckUnitBinding
import com.app.hack_brain.extension.gone
import com.app.hack_brain.model.uimodel.Unit

class UnitAdapter(
    private val context: Context,
    private val onItemClicked: (unit: Unit) -> kotlin.Unit
) : BaseRecyclerViewAdapter<Unit, UnitAdapter.CheckItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckItemViewHolder {
        return CheckItemViewHolder(
            ItemCheckUnitBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClicked
        )
    }

    override fun onBindViewHolder(holder: CheckItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(context, it)
            holder.bindItemClick(it)
        }
    }

    inner class CheckItemViewHolder(
        private val itemBinding: ItemCheckUnitBinding,
        private val onItemClicked: (unit: Unit) -> kotlin.Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(context: Context, unit: Unit) {
            itemBinding.run {
                tvUnit.text = unit.unit
                tvEngViePercent.text = unit.engViePercent
                tvVieEngPercent.text = unit.vieEngPercent
                tvSoundPercent.text = unit.soundPercent
                setDisableUnit(context, unit.isDisable)
            }
        }

        fun bindItemClick(unit: Unit) {
            itemBinding.run {
                cvUnit.setOnClickListener {
                    onItemClicked(unit)
                }
            }
        }

        private fun setDisableUnit(context: Context, isDisable: Boolean = false) {
            itemBinding.run {
                tvEngVie.setTextColor(ContextCompat.getColor(context, if (isDisable) R.color.border_gray else R.color.black))
                tvVieEng.setTextColor(ContextCompat.getColor(context, if (isDisable) R.color.border_gray else R.color.black))
                tvSound.setTextColor(ContextCompat.getColor(context, if (isDisable) R.color.border_gray else R.color.black))
                tvEngViePercent.setTextColor(ContextCompat.getColor(context, if (isDisable) R.color.border_gray else R.color.light_red))
                tvVieEngPercent.setTextColor(ContextCompat.getColor(context, if (isDisable) R.color.border_gray else R.color.light_red))
                tvSoundPercent.setTextColor(ContextCompat.getColor(context, if (isDisable) R.color.border_gray else R.color.light_red))
                ivLock.gone(!isDisable)
            }
        }
    }
}