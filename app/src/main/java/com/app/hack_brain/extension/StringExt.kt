package com.app.hack_brain.extension

fun String.appendPercent() = "$this%"

fun String?.nullToBlank() = this ?: ""