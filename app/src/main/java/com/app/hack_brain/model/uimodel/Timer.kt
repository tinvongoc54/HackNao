package com.app.hack_brain.model.uimodel

data class Timer(
    val time: String,
    val isTurnOn: Boolean,
    val isEveryMonday: Boolean,
    val isEveryTuesday: Boolean,
    val isEveryWednesday: Boolean,
    val isEveryThursday: Boolean,
    val isEveryFriday: Boolean,
    val isEverySaturday: Boolean,
    val isEverySunday: Boolean
) {
    fun getStringCalendar(): String {
        var string = ""
        if (isEveryMonday) string += "Thứ Hai, "
        if (isEveryTuesday) string += "Thứ Ba, "
        if (isEveryWednesday) string += "Thứ Tư, "
        if (isEveryThursday) string += "Thứ Năm, "
        if (isEveryFriday) string += "Thứ Sáu, "
        if (isEverySaturday) string += "Thứ Bảy, "
        if (isEverySunday) string += "Chủ Nhật"
        return string
    }
}