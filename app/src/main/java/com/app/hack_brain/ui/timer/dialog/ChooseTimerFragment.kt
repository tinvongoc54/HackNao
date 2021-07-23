package com.app.hack_brain.ui.timer.dialog

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.navArgs
import com.app.hack_brain.R
import com.app.hack_brain.common.Constant
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.data.local.entity.TimerEntity
import com.app.hack_brain.databinding.FragmentChooseTimerBinding
import com.app.hack_brain.extension.format2Number
import com.app.hack_brain.extension.gone
import com.app.hack_brain.extension.showDialogChooseTime
import com.app.hack_brain.ui.home.HomeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialSharedAxis
import timber.log.Timber
import java.util.*

class ChooseTimerFragment(
) : BaseFragment<ChooseTimerFragViewModel, FragmentChooseTimerBinding>(ChooseTimerFragViewModel::class) {

    private val args: ChooseTimerFragmentArgs by navArgs()
    private lateinit var timer: TimerEntity
    private var isOpenApp = false
    private var isCreateNew = false

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChooseTimerBinding {
        return FragmentChooseTimerBinding.inflate(inflater)
    }

    override fun onStop() {
        super.onStop()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initialize() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        isCreateNew = args.isCreateNew
        isOpenApp = args.isOpenApp
        timer = args.timer
        bindDataTimer()

        viewBinding.run {
            clNumberRepeat.gone(isOpenApp)
            clVocabulary.gone(isOpenApp)
            clWait.gone(isOpenApp)

            tvTime.setOnClickListener {
                chooseTime()
            }

            clRepeat.setOnClickListener {
                chooseRepeat()
            }

            clVocabulary.setOnClickListener {
                chooseVocabulary()
            }

            clNumberRepeat.setOnClickListener {
                chooseNumberRepeat()
            }

            clWait.setOnClickListener {
                chooseWaitingTime()
            }
            activity?.findViewById<AppCompatTextView>(R.id.tvDone)?.setOnClickListener {
                viewModel.run {
                    if (isCreateNew) {
                        insertTimer(timer)
                    } else {
                        updateTimer(timer)
                        if (timer.isTurnOn) {
                            if (isOpenApp) {
                                (activity as? HomeActivity)?.alarmService?.setRepeatOpenApp(timer.time, timer.repeat)
                            } else {
                                (activity as? HomeActivity)?.alarmService?.setRepeatRemindVoc(timer)
                            }
                        }
                    }
                }
                activity?.onBackPressed()
            }
        }
    }

    private fun bindDataTimer() {
        viewBinding.run {
            tvTime.text = timer.getHour()
            tvTimeRepeat.text = timer.getStringCalendar()

            if (isOpenApp.not()) {
                tvNumberRepeat.text = timer.getRepeatNumbers()
                tvVocabulary.text = timer.getListVocabulary()
                tvWait.text = timer.getWaitingTimeString()
            }
        }
    }

    private fun chooseTime() {
        val time = viewBinding.tvTime.text.toString().split(":")
        requireContext().showDialogChooseTime(
            time[0].trim().toInt(),
            time[1].trim().toInt()
        ) { hour, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            timer.time = calendar.timeInMillis
            viewBinding.tvTime.text = String.format("%s:%s", hour.format2Number(), minute.format2Number())
        }
    }

    private fun chooseRepeat() {
        val multiItems = resources.getStringArray(R.array.repeat)
        val checkedItems = timer.getListBoolean().toBooleanArray()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hẹn giờ mở ứng dụng")
            .setMultiChoiceItems(multiItems, checkedItems) { dialog, which, checked ->
                checkedItems[which] = checked
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                timer.repeat = convertToRepeatString(checkedItems.toList())
                Timber.i("repeat: ${timer.repeat}")
                viewBinding.tvTimeRepeat.text = timer.getStringCalendar()
            }
            .show()
    }

    private fun convertToRepeatString(checkedItems: List<Boolean>): String {
        val repeat = mutableListOf<String>()
        checkedItems.forEachIndexed { index, bool ->
            if (bool)
                repeat.add((index + 1).toString())
        }
        return repeat.joinToString(",")
    }

    private fun chooseVocabulary() {
        val options = mutableListOf("Yêu thích")
        var checked = timer.vocabulary ?: 0
        var vocabulary = ""
        for (i in 1..Constant.TOTAL_UNIT) {
            options.add("Unit $i")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chọn từ vựng từ danh sách")
            .setSingleChoiceItems(options.toTypedArray(), checked) { _, which ->
                checked = which
                vocabulary = options[which]
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                timer.vocabulary = checked
                Timber.i("vocabulary: ${timer.vocabulary}")
                viewBinding.tvVocabulary.text = vocabulary
            }
            .show()
    }

    private fun chooseNumberRepeat() {
        val options = resources.getStringArray(R.array.number_repeat)
        val checked = timer.getPositionRepeatNumber()
        var numberRepeat = options[checked]

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chọn số lần lặp")
            .setSingleChoiceItems(options, checked) { _, which ->
                numberRepeat = options[which]
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                timer.repeatNumber = numberRepeat[0].toString().toInt()
                Timber.i("repeat number: ${timer.repeatNumber}")
                viewBinding.tvNumberRepeat.text = numberRepeat
            }
            .show()
    }

    private fun chooseWaitingTime() {
        val second = resources.getIntArray(R.array.waiting_time_second)
        val options = resources.getStringArray(R.array.waiting_time)
        var checked = second.indexOf(timer.waitingTime ?: 5)
        Timber.i("waiting time before: ${timer.waitingTime}")
        var waitingTime = ""

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chọn thời gian chờ")
            .setSingleChoiceItems(options, checked) { _, which ->
                checked = which
                waitingTime = options[which]
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                timer.waitingTime = second[checked]
                Timber.i("waiting time: ${timer.waitingTime}")
                viewBinding.tvWait.text = waitingTime
            }
            .show()
    }
}