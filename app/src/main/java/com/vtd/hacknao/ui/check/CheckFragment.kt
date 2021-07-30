package com.vtd.hacknao.ui.check

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.vtd.hacknao.R
import com.vtd.hacknao.common.Constant
import com.vtd.hacknao.common.base.BaseFragment
import com.vtd.hacknao.data.local.entity.UnitEntity
import com.vtd.hacknao.databinding.FragmentCheckBinding
import com.vtd.hacknao.extension.navigateWithSlideAnim
import com.vtd.hacknao.extension.nullToZero
import com.vtd.hacknao.extension.showMessage
import com.google.android.material.transition.MaterialSharedAxis

class CheckFragment :
    BaseFragment<CheckFragViewModel, FragmentCheckBinding>(CheckFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckBinding {
        return FragmentCheckBinding.inflate(inflater)
    }

    override fun onStop() {
        super.onStop()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun initialize() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        viewModel.getUnitList()
        initUnitAdapter()
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            unitListEvent.observe(viewLifecycleOwner, Observer {
                (viewBinding.rvUnit.adapter as UnitAdapter).replaceData(it.toMutableList())
            })
        }
    }

    private fun initUnitAdapter() {
        viewBinding.rvUnit.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = UnitAdapter(
                context = requireContext(),
                onClickEngVie = {
                    doClick(it, Constant.TypeCheckClick.TYPE_ENG_VIE)
                },
                onClickVieEng = {
                    doClick(it, Constant.TypeCheckClick.TYPE_VIE_ENG)
                },
                onClickSound = {
                    doClick(it, Constant.TypeCheckClick.TYPE_SOUND)
                }
            )
        }
    }

    private fun doClick(unit: UnitEntity, typeClick: Constant.TypeCheckClick) {
        if (unit.isEnable == false) {
            showMessage(getString(R.string.text_warning_unit, ((unit.unit ?: 2) - 1).toString()))
        } else {
            when (typeClick) {
                Constant.TypeCheckClick.TYPE_ENG_VIE -> navigateToDetailCheckEngVieUnit(unit.unit.nullToZero())
                Constant.TypeCheckClick.TYPE_VIE_ENG -> navigateToDetailCheckVieEngUnit(unit.unit.nullToZero())
                Constant.TypeCheckClick.TYPE_SOUND -> navigateToDetailCheckSoundUnit(unit.unit.nullToZero())
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