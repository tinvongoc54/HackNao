package com.app.hack_brain.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "muctieu", indices = [Index(value = ["id"], unique = true)])
data class TargetEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "unit") var unit: Int,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "status") var status: Int
)