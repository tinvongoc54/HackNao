package com.vtd.hacknao.data.local.sharedpfers

import com.vtd.hacknao.common.base.BaseRepository
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

    override fun setIsOpenedApp(isOpened: Boolean) {
        sharedPrefsWrapper[SharedPrefKeys.SHARED_IS_OPENED_APP] = isOpened
    }

    override fun isOpenedApp(): Boolean {
        return sharedPrefsWrapper[SharedPrefKeys.SHARED_IS_OPENED_APP]
    }
}

interface SharePrefsService {
    fun setTargetUnitNumberOfDay(number: Int)
    fun getTargetUnitNumberOfDay(): Int

    fun setIsOpenedApp(isOpened: Boolean)
    fun isOpenedApp(): Boolean
}