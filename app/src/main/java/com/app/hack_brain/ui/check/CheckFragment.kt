package com.app.hack_brain.ui.check

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentCheckBinding
import com.app.hack_brain.model.uimodel.Unit

class CheckFragment : BaseFragment<CheckFragViewModel, FragmentCheckBinding>(CheckFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckBinding {
        return FragmentCheckBinding.inflate(inflater)
    }

    override fun initialize() {
        val list = mutableListOf<Unit>()
        list.add(Unit("Unit 1", "20%", "30%", "0%"))
        list.add(Unit("Unit 1", "20%", "30%", "0%"))
        list.add(Unit("Unit 1", "20%", "30%", "0%"))
        list.add(Unit("Unit 1", "20%", "30%", "0%"))
        list.add(Unit("Unit 1", "20%", "30%", "0%", true))
        list.add(Unit("Unit 1", "20%", "30%", "0%", true))
        list.add(Unit("Unit 1", "20%", "30%", "0%", true))
        list.add(Unit("Unit 1", "20%", "30%", "0%", true))
        list.add(Unit("Unit 1", "20%", "30%", "0%", true))
        list.add(Unit("Unit 1", "20%", "30%", "0%", true))
        list.add(Unit("Unit 1", "20%", "30%", "0%", true))

        initUnitAdapter(list)
    }

    private fun initUnitAdapter(list: MutableList<Unit>) {
        viewBinding.rvUnit.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = UnitAdapter(requireContext()) {

            }
            with(adapter as UnitAdapter) {
                replaceData(list)
            }
        }
    }
}