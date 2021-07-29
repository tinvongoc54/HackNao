package com.app.hack_brain.ui.check

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseRecyclerViewAdapter
import com.app.hack_brain.data.local.entity.UnitEntity
import com.app.hack_brain.databinding.ItemCheckUnitBinding
import com.app.hack_brain.extension.appendPercent
import com.app.hack_brain.extension.gone

class UnitAdapter(
    private val context: Context,
    private val onClickEngVie: (unit: UnitEntity) -> Unit,
    private val onClickVieEng: (unit: UnitEntity) -> Unit,
    private val onClickSound: (unit: UnitEntity) -> Unit
) : BaseRecyclerViewAdapter<UnitEntity, UnitAdapter.CheckItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckItemViewHolder {
        return CheckItemViewHolder(
            ItemCheckUnitBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickEngVie, onClickVieEng, onClickSound
        )
    }

    override fun onBindViewHolder(holder: CheckItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(context, it, position)
            holder.bindItemClick(it)
        }
    }

    inner class CheckItemViewHolder(
        private val itemBinding: ItemCheckUnitBinding,
        private val onClickEngVie: (unit: UnitEntity) -> Unit,
        private val onClickVieEng: (unit: UnitEntity) -> Unit,
        private val onClickSound: (unit: UnitEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(context: Context, unit: UnitEntity, position: Int) {
            itemBinding.run {
                tvUnit.text = String.format("Unit %s", unit.unit.toString())
                tvEngViePercent.text = unit.progressEngVie.toString().appendPercent()
                tvVieEngPercent.text = unit.progressVieEng.toString().appendPercent()
                tvSoundPercent.text = unit.progressSound.toString().appendPercent()
                setEnableUnit(context, position, if (position > 0) getData()[position - 1].isEnableNextUnit() else true)
            }
        }

        fun bindItemClick(unit: UnitEntity) {
            itemBinding.run {
                clEngVie.setOnClickListener {
                    onClickEngVie(unit)
                }
                clVieEng.setOnClickListener {
                    onClickVieEng(unit)
                }
                clSound.setOnClickListener {
                    onClickSound(unit)
                }
            }
        }

        private fun setEnableUnit(context: Context, position: Int, isEnable: Boolean = false) {
            itemBinding.run {
                getItem(position)?.isEnable = isEnable
                tvEngVie.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isEnable.not()) R.color.border_gray else R.color.black
                    )
                )
                tvVieEng.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isEnable.not()) R.color.border_gray else R.color.black
                    )
                )
                tvSound.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isEnable.not()) R.color.border_gray else R.color.black
                    )
                )
                tvEngViePercent.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isEnable.not()) R.color.border_gray else R.color.light_red
                    )
                )
                tvVieEngPercent.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isEnable.not()) R.color.border_gray else R.color.light_red
                    )
                )
                tvSoundPercent.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isEnable.not()) R.color.border_gray else R.color.light_red
                    )
                )
                ivLock.gone(isEnable)
            }
        }
    }
}