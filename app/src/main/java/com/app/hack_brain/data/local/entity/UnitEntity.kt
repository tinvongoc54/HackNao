package com.app.hack_brain.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.app.hack_brain.extension.nullToZero
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "unit_tracking", indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class UnitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "unit") var unit: Int?,
    @ColumnInfo(name = "progress_av") var progressEngVie: Int? = 0,
    @ColumnInfo(name = "progress_va") var progressVieEng: Int? = 0,
    @ColumnInfo(name = "progress_at") var progressSound: Int? = 0,
    var isEnable: Boolean? = false
) : Parcelable {
    fun isEnableNextUnit() =
        progressEngVie.nullToZero() >= 50 && progressVieEng.nullToZero() >= 50 && progressSound.nullToZero() >= 50
}