package com.app.hack_brain.ui.timer.dialog

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentChooseTimerBinding
import com.app.hack_brain.extension.gone
import com.app.hack_brain.extension.showDialogChooseTime
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChooseTimerFragment(
) : BaseFragment<ChooseTimerFragViewModel, FragmentChooseTimerBinding>(ChooseTimerFragViewModel::class) {

    private val args: ChooseTimerFragmentArgs by navArgs()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChooseTimerBinding {
        return FragmentChooseTimerBinding.inflate(inflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initialize() {
        val isOpenApp = args.isOpenApp

        viewBinding.run {
            clNumberRepeat.gone(isOpenApp)
            clVocabulary.gone(isOpenApp)
            clWait.gone(isOpenApp)

            tvTime.setOnClickListener {
                val time = tvTime.text.toString().split(":")
                requireContext().showDialogChooseTime(
                    time[0].trim().toInt(),
                    time[1].trim().toInt()
                ) { hour, minute ->
                    tvTime.text = String.format("%s:%s", hour, minute)
                }
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
        }
    }

    private fun chooseRepeat() {
        val multiItems = resources.getStringArray(R.array.repeat)
        val checkedItems = booleanArrayOf(false, true, true, true, true, true, false)
        var time = ""

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hẹn giờ mở ứng dụng")
            .setMultiChoiceItems(multiItems, checkedItems) { dialog, which, checked ->
                when (which) {
                    0 -> if (checked) time += "Chủ Nhật, "
                    1 -> if (checked) time += "Thứ Hai, "
                    2 -> if (checked) time += "Thứ Ba, "
                    3 -> if (checked) time += "Thứ Tư, "
                    4 -> if (checked) time += "Thứ Năm, "
                    5 -> if (checked) time += "Thứ Sáu, "
                    6 -> if (checked) time += "Thứ Bảy, "
                }
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                viewBinding.tvTimeRepeat.text = time
            }
            .show()
    }

    private fun chooseVocabulary() {
        val multiItems = mutableListOf("Yêu thích")
        val checked = mutableListOf(true)
        for (i in 1..150) {
            multiItems.add("Unit $i")
            checked.add(false)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chọn từ vựng từ danh sách")
            .setMultiChoiceItems(multiItems.toTypedArray(), checked.toBooleanArray()) { dialog, which, checked ->
                when (which) {

                }
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->

            }
            .show()
    }

    private fun chooseNumberRepeat() {
        val options = resources.getStringArray(R.array.number_repeat)
        val checked = 0
        var numberRepeat = ""

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chọn số lần lặp")
            .setSingleChoiceItems(options, checked) { _, which ->
                numberRepeat = options[which]
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                viewBinding.tvNumberRepeat.text = numberRepeat
            }
            .show()
    }

    private fun chooseWaitingTime() {
        val options = resources.getStringArray(R.array.waiting_time)
        val checked = 0
        var waitingTime = ""

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chọn thời gian chờ")
            .setSingleChoiceItems(options, checked) { _, which ->
                waitingTime = options[which]
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { dialogInterface, _ ->
                viewBinding.tvWait.text = waitingTime
            }
            .show()
    }
}