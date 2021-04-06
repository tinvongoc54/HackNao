package com.app.hack_brain.ui.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentTimerBinding
import com.app.hack_brain.model.uimodel.Timer
import timber.log.Timber

class TimerFragment : BaseFragment<TimerFragViewModel, FragmentTimerBinding>(TimerFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimerBinding {
        return FragmentTimerBinding.inflate(inflater)
    }

    override fun initialize() {
        val list = mutableListOf<Timer>()
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
        initTimerOpenApp(list)
    }

    private fun initTimerOpenApp(list: MutableList<Timer>) {
        viewBinding.rvOpenApp.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TimerAdapter({
                Timber.i("click")
            }, {
                Timber.i("edit")
            }, {
                Timber.i("delete")
            })
            with(adapter as TimerAdapter) {
                replaceData(list)
            }
        }
    }
}