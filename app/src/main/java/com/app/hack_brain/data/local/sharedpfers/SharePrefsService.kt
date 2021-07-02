package com.app.hack_brain.data.local.sharedpfers

import com.app.hack_brain.common.base.BaseRepository
import com.google.gson.Gson

class DefaultSharePrefsService(
    private val sharedPrefsWrapper: SharedPrefsWrapper,
    private val gson: Gson
) : BaseRepository(), SharePrefsService {
}

interface SharePrefsService {
}