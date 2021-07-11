package com.app.hack_brain.data.local.sharedpfers

import com.app.hack_brain.common.base.BaseRepository
import com.google.gson.Gson

class DefaultSharePrefsService(
    private val sharedPrefsWrapper: SharedPrefsWrapper,
    private val gson: Gson
) : BaseRepository(), SharePrefsService {
    override fun setTargetUnitNumberOfDay(number: Int) {
        sharedPrefsWrapper[SharedPrefKeys.SHARED_UNIT_NUMBER] = number
    }

    override fun getTargetUnitNumberOfDay(): Int {
        return sharedPrefsWrapper[SharedPrefKeys.SHARED_UNIT_NUMBER]
    }
}

interface SharePrefsService {
    fun setTargetUnitNumberOfDay(number: Int)
    fun getTargetUnitNumberOfDay(): Int
}