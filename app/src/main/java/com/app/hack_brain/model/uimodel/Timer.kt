package com.app.hack_brain.model.uimodel

data class Timer(
    var time: String,
    var isTurnOn: Boolean,
    var isEveryMonday: Boolean,
    var isEveryTuesday: Boolean,
    var isEveryWednesday: Boolean,
    var isEveryThursday: Boolean,
    var isEveryFriday: Boolean,
    var isEverySaturday: Boolean,
    var isEverySunday: Boolean
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