package com.app.hack_brain.ui.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.data.local.entity.TimerEntity
import com.app.hack_brain.databinding.FragmentTimerBinding
import com.app.hack_brain.extension.navigateWithSlideAnim
import com.google.android.material.transition.MaterialSharedAxis
import timber.log.Timber

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
        }
    }

    private fun initTimerOpenApp() {
        viewBinding.rvOpenApp.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TimerAdapter(
                onClickItem = {
                    navigateToTimerDialog(true, it)
                },
                onClickEditItem = {
                    Timber.i("edit")
                },
                onClickDeleteItem = {
                    Timber.i("delete")
                },
                onClickCheckBox = {

                })
        }
    }

    private fun initTimerRemindVocabulary() {
        viewBinding.rvRemindVocabulary.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TimerAdapter(
                onClickItem = {
                    navigateToTimerDialog(false, it)
                },
                onClickEditItem = {
                    Timber.i("edit")
                },
                onClickDeleteItem = {
                    Timber.i("delete")
                },
                onClickCheckBox = {

                }
            )
        }
    }

    private fun navigateToTimerDialog(isOpenApp: Boolean, timer: TimerEntity) {
        val action = TimerFragmentDirections.actionTimerToTimerDialogFragment(isOpenApp, timer)
        navigateWithSlideAnim(action)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notifyReminderChannel"
            val description = "Channel for alarm"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("notifycc", name, importance)
            channel.description = description

            val notificationManager =
                requireContext().getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}