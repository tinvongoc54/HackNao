package com.app.hack_brain.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "pushlocal", indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class TimerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "time") var time: Long?,
    @ColumnInfo(name = "laplai") var repeat: String?,
    @ColumnInfo(name = "tuvung") var vocabulary: String?,
    @ColumnInfo(name = "type") var type: Int?,
    @ColumnInfo(name = "thoigiancho") var waitingTime: Int?,
    @ColumnInfo(name = "isturnon") var isTurnOn: Int?,
    @ColumnInfo(name = "solanlap") var repeatNumber: Int?
): Parcelable