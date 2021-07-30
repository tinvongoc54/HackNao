package com.vtd.hacknao.extension

fun String.appendPercent() = "$this%"

fun String?.nullToBlank() = this ?: ""