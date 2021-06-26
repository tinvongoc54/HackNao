package com.app.hack_brain.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "muctieu", indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class TargetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "unit") var unit: Int?,
    @ColumnInfo(name = "date") var date: String?,
    @ColumnInfo(name = "status") var status: Int?
): Parcelable