package com.app.hack_brain.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentTimerBinding
import com.app.hack_brain.extension.navigateWithSlideAnim
import com.app.hack_brain.model.uimodel.Timer
import com.app.hack_brain.ui.timer.dialog.ChooseTimerFragment
import timber.log.Timber

class TimerFragment : BaseFragment<TimerFragViewModel, FragmentTimerBinding>(TimerFragViewModel::class) {

    private val list = mutableListOf<Timer>()
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimerBinding {
        return FragmentTimerBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list.add(Timer(
            time = "08:00",
            isTurnOn = false,
            isEveryMonday = true,
            isEveryTuesday = true,
            isEveryWednesday = true,
            isEveryThursday = true,
            isEveryFriday = true,
            isEverySaturday = false,
            isEverySunday = false
        ))
        list.add(Timer(
            time = "09:00",
            isTurnOn = false,
            isEveryMonday = true,
            isEveryTuesday = true,
            isEveryWednesday = true,
            isEveryThursday = true,
            isEveryFriday = true,
            isEverySaturday = false,
            isEverySunday = false
        ))
    }

    override fun initialize() {
        initTimerOpenApp(list)
        initTimerRemindVocabulary(list)
    }

    private fun initTimerOpenApp(list: MutableList<Timer>) {
        viewBinding.rvOpenApp.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TimerAdapter({
                navigateToTimerDialog(true)
            }, {
                Timber.i("edit")
            }, {
                Timber.i("delete")
            }, {

            })
            with(adapter as TimerAdapter) {
                replaceData(list)
            }
        }
    }

    private fun initTimerRemindVocabulary(list: MutableList<Timer>) {
        viewBinding.rvRemindVocabulary.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TimerAdapter({
                navigateToTimerDialog(false)
            }, {
                Timber.i("edit")
            }, {
                Timber.i("delete")
            }, {

            })
            with(adapter as TimerAdapter) {
                replaceData(list)
            }
        }
    }

    private fun navigateToTimerDialog(isOpenApp: Boolean) {
        val action = TimerFragmentDirections.actionTimerToTimerDialogFragment(isOpenApp)
        navigateWithSlideAnim(action)
    }
}