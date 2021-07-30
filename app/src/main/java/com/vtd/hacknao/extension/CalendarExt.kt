package com.vtd.hacknao.extension

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val DATE_FORMAT_EN = "dd/MM/yyyy"

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.formatByPattern(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return this.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.formatByPattern(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return this.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toStringEnFormat() = formatByPattern(DATE_FORMAT_EN)

@SuppressLint("SimpleDateFormat")
fun convertTimestampToDate(time: Long?): String {
    if (time == null) return ""
    val date = Date(time)
    val format = SimpleDateFormat(DATE_FORMAT_EN)
    return format.format(date)
}

@SuppressLint("SimpleDateFormat")
fun toDate(date: String?): Date {
    return SimpleDateFormat(DATE_FORMAT_EN).parse(date.nullToBlank()) ?: Date()
}