package com.app.hack_brain.ui.check

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.data.local.entity.UnitEntity
import com.app.hack_brain.databinding.FragmentCheckBinding
import com.app.hack_brain.extension.navigateWithSlideAnim
import com.app.hack_brain.extension.nullToZero
import com.app.hack_brain.model.uimodel.Unit

class CheckFragment : BaseFragment<CheckFragViewModel, FragmentCheckBinding>(CheckFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckBinding {
        return FragmentCheckBinding.inflate(inflater)
    }

    override fun initialize() {
        viewModel.getUnitList()
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            unitListEvent.observe(viewLifecycleOwner, Observer{
                initUnitAdapter(it.toMutableList())
            })
        }
    }

    private fun initUnitAdapter(list: MutableList<UnitEntity>) {
        viewBinding.rvUnit.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = UnitAdapter(
                context = requireContext(),
                onClickEngVie = {
                    navigateToDetailCheckEngVieUnit(it.unit.nullToZero())
                },
                onClickVieEng = {
                    navigateToDetailCheckVieEngUnit(it.unit.nullToZero())
                },
                onClickSound = {
                    navigateToDetailCheckSoundUnit(it.unit.nullToZero())
                }
            )
            with(adapter as UnitAdapter) {
                replaceData(list)
            }
        }
    }

    private fun navigateToDetailCheckEngVieUnit(unit: Int) {
        val action = CheckFragmentDirections.actionToCheckEngVieFragment(unit, null)
        navigateWithSlideAnim(action)
    }

    private fun navigateToDetailCheckVieEngUnit(unit: Int) {
        val action = CheckFragmentDirections.actionToCheckVieEngFragment(unit, null)
        navigateWithSlideAnim(action)
    }

    private fun navigateToDetailCheckSoundUnit(unit: Int) {
        val action = CheckFragmentDirections.actionToCheckSoundFragment(unit, null)
        navigateWithSlideAnim(action)
    }
}