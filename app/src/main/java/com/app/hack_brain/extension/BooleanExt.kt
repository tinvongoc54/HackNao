package com.app.hack_brain.extension

/**
 * Created by ThuanPx on 3/15/20.
 */

fun Boolean?.isTrue() = this == true

fun Boolean?.isNotTrue() = !this.isTrue()

fun Boolean?.toInt() = if (this == true) 1 else 0
