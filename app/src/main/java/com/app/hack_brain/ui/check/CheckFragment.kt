package com.app.hack_brain.ui.check

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentCheckBinding
import com.app.hack_brain.extension.navigateWithSlideAnim
import com.app.hack_brain.model.uimodel.Unit

class CheckFragment : BaseFragment<CheckFragViewModel, FragmentCheckBinding>(CheckFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckBinding {
        return FragmentCheckBinding.inflate(inflater)
    }

    override fun initialize() {
//        viewModel.insert()
        viewModel.getVoc()
//        val unitList = mutableListOf<Unit>()
//        for (i in 1..150) {
//            val unit = Unit(unit = "Unit $i", words = databaseHelper.getAllWord(i))
//            unitList.add(unit)
//        }
//        initUnitAdapter(unitList)
    }

    private fun initUnitAdapter(list: MutableList<Unit>) {
        viewBinding.rvUnit.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = UnitAdapter(
                context = requireContext(),
                onClickEngVie = {
                    navigateToDetailCheckEngVieUnit(it)
                },
                onClickVieEng = {
                    navigateToDetailCheckVieEngUnit(it)
                },
                onClickSound = {
                    navigateToDetailCheckSoundUnit(it)
                }
            )
            with(adapter as UnitAdapter) {
                replaceData(list)
            }
        }
    }

    private fun navigateToDetailCheckEngVieUnit(unit: Unit) {
        val action = CheckFragmentDirections.actionToCheckEngVieFragment(unit)
        navigateWithSlideAnim(action)
    }

    private fun navigateToDetailCheckVieEngUnit(unit: Unit) {
        val action = CheckFragmentDirections.actionToCheckVieEngFragment(unit)
        navigateWithSlideAnim(action)
    }

    private fun navigateToDetailCheckSoundUnit(unit: Unit) {
        val action = CheckFragmentDirections.actionToCheckEngVieFragment(unit)
        navigateWithSlideAnim(action)
    }
}