package com.vtd.hacknao.ui.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vtd.hacknao.R
import com.vtd.hacknao.common.Constant
import com.vtd.hacknao.common.base.BaseFragment
import com.vtd.hacknao.data.local.entity.TimerEntity
import com.vtd.hacknao.databinding.FragmentTimerBinding
import com.vtd.hacknao.extension.navigateWithSlideAnim
import com.vtd.hacknao.ui.home.HomeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialSharedAxis
import java.util.*

class TimerFragment :
    BaseFragment<TimerFragViewModel, FragmentTimerBinding>(TimerFragViewModel::class) {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimerBinding {
        return FragmentTimerBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
    }

    override fun onStop() {
        super.onStop()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun initialize() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        viewModel.getTimerList()
        initTimerOpenApp()
        initTimerRemindVocabulary()

        activity?.findViewById<AppCompatImageView>(R.id.ivAdd)?.setOnClickListener {
            chooseCreateTimer()
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            openAppTimerList.observe(viewLifecycleOwner, Observer {
                (viewBinding.rvOpenApp.adapter as TimerAdapter).replaceData(it.toMutableList())
            })
            remindVocTimerList.observe(viewLifecycleOwner, Observer {
                (viewBinding.rvRemindVocabulary.adapter as TimerAdapter).replaceData(it.toMutableList())
            })
            deleteSuccess.observe(viewLifecycleOwner, Observer {
                if (it) {
                    (viewBinding.rvOpenApp.adapter as? TimerAdapter)?.removeItem(deletePosition, isNotifyAll = true)
                } else {
                    (viewBinding.rvRemindVocabulary.adapter as? TimerAdapter)?.removeItem(deletePosition, isNotifyAll = true)
                }
            })
        }
    }

    private fun initTimerOpenApp() {
        viewBinding.rvOpenApp.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TimerAdapter(
                isOpenApp = true,
                onClickItem = {
                    navigateToTimerDialog(isCreateNew = false, isOpenApp = true, timer = it)
                },
                onClickEditItem = {
                    navigateToTimerDialog(isCreateNew = false, isOpenApp = true, timer = it)
                },
                onClickDeleteItem = { timer, position ->
                    viewModel.deleteTimer(timer, isOpenApp = true, position = position)
                },
                onClickCheckBox = {
                    viewModel.updateTimer(it)
                    if (it.isTurnOn) {
                        (activity as? HomeActivity)?.alarmService?.setRepeatOpenApp(it.time, it.repeat)
                    } else {
                        (activity as? HomeActivity)?.alarmService?.cancelAlarmOpenApp()
                    }
                })
        }
    }

    private fun initTimerRemindVocabulary() {
        viewBinding.rvRemindVocabulary.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TimerAdapter(
                isOpenApp = false,
                onClickItem = {
                    navigateToTimerDialog(isCreateNew = false, isOpenApp = false, timer = it)
                },
                onClickEditItem = {
                    navigateToTimerDialog(isCreateNew = false, isOpenApp = false, timer = it)
                },
                onClickDeleteItem = { timer, position ->
                    viewModel.deleteTimer(timer, isOpenApp = false, position = position)
                },
                onClickCheckBox = {
                    viewModel.updateTimer(it)
                    if (it.isTurnOn) {
                        (activity as? HomeActivity)?.alarmService?.setRepeatRemindVoc(it)
                    } else {
                        (activity as? HomeActivity)?.alarmService?.cancelAlarmRemindVoc()
                    }
                }
            )
        }
    }

    private fun chooseCreateTimer() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Tạo hẹn giờ")
            .setItems(resources.getStringArray(R.array.create_timer)) { _, which ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.SECOND, 0)
                val timer = TimerEntity(
                    id = viewModel.newIdTimer,
                    time = calendar.timeInMillis,
                    repeat = "1,2,3,4,5,6,7",
                    type = Constant.TYPE_OPEN_APP,
                    vocabulary = null,
                    waitingTime = null,
                    repeatNumber = null,
                    isTurnOn = false
                )
                when (which) {
                    0 -> navigateToTimerDialog(isCreateNew = true, isOpenApp = true, timer = timer)
                    1 -> {
                        timer.type = Constant.TYPE_REMIND_VOCABULARY
                        timer.vocabulary = 0
                        timer.waitingTime = 15
                        timer.repeatNumber = 1
                        navigateToTimerDialog(isCreateNew = true, isOpenApp = false, timer = timer)
                    }
                }
            }
            .show()
    }

    private fun navigateToTimerDialog(
        isCreateNew: Boolean,
        isOpenApp: Boolean,
        timer: TimerEntity
    ) {
        val action =
            TimerFragmentDirections.actionTimerToTimerDialogFragment(isCreateNew, isOpenApp, timer)
        navigateWithSlideAnim(action)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notifyReminderChannel"
            val description = "Channel for alarm"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constant.CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager =
                requireContext().getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}