package com.vtd.hacknao.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vtd.hacknao.extension.format2Number
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "pushlocal", indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class TimerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "time") var time: Long,
    @ColumnInfo(name = "laplai") var repeat: String,
    @ColumnInfo(name = "tuvung") var vocabulary: Int?,
    @ColumnInfo(name = "type") var type: Int?,
    @ColumnInfo(name = "thoigiancho") var waitingTime: Int?,
    @ColumnInfo(name = "isturnon") var isTurnOn: Boolean,
    @ColumnInfo(name = "solanlap") var repeatNumber: Int?
) : Parcelable {
    fun getHour(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return String.format(
            "%s:%s",
            calendar.get(Calendar.HOUR_OF_DAY).format2Number(),
            calendar.get(Calendar.MINUTE).format2Number()
        )
    }

    fun getRepeatNumbers() = String.format("%s lần", repeatNumber)

    fun getPositionRepeatNumber() = when (repeatNumber) {
        1 -> 0
        3 -> 1
        5 -> 2
        else -> 0
    }

    fun getListVocabulary(): String {
        if (vocabulary == 0) return "Yêu thích"
        return "Unit $vocabulary"
    }

    fun getStringCalendar(): String {
        if (repeat == "1,2,3,4,5,6,7") return "Tất cả các ngày"
        val result = mutableListOf<String>()
        if (repeat.contains("1")) result.add("Chủ Nhật")
        if (repeat.contains("2")) result.add("Thứ Hai")
        if (repeat.contains("3")) result.add("Thứ Ba")
        if (repeat.contains("4")) result.add("Thứ Tư")
        if (repeat.contains("5")) result.add("Thứ Năm")
        if (repeat.contains("6")) result.add("Thứ Sáu")
        if (repeat.contains("7")) result.add("Thứ Bảy")
        return result.joinToString(",")
    }

    fun getListBoolean(): List<Boolean> {
        val result = mutableListOf<Boolean>()
        result.add(repeat.contains("1"))
        result.add(repeat.contains("2"))
        result.add(repeat.contains("3"))
        result.add(repeat.contains("4"))
        result.add(repeat.contains("5"))
        result.add(repeat.contains("6"))
        result.add(repeat.contains("7"))
        return result
    }

    fun getWaitingTimeString(): String {
        waitingTime?.let {
            val minute = it / 60
            return if (minute > 0) {
                "$minute phút"
            } else {
                "$it giây"
            }
        }
        return ""
    }
}