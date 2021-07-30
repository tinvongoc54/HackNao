package com.vtd.hacknao.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vtd.hacknao.R
import com.vtd.hacknao.extension.nullToBlank
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "dict_en_vi")
@Parcelize
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "word") val word: String?,
    @ColumnInfo(name = "phonetic") val phonetic: String?,
    @ColumnInfo(name = "meanings") val meanings: String?,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "is_favorite") var isFavourite: Int?,
    @ColumnInfo(name = "is_learn_va") var isLearnedVieEng: Int?,
    @ColumnInfo(name = "is_learn_av") var isLearnedEngVie: Int?,
    @ColumnInfo(name = "is_learn_at") var isLearnedSound: Int?,
    @ColumnInfo(name = "short_mean") var shortMean: String?
): Parcelable {
    fun getTypeOfVoc(): String {
        val type = meanings?.substring(0, 15).nullToBlank()
        return when {
            type.contains("động từ", ignoreCase = true) -> "(v)"
            type.contains("danh từ", ignoreCase = true) -> "(n)"
            type.contains("tính từ", ignoreCase = true) -> "(adj)"
            type.contains("phó từ", ignoreCase = true) || meanings?.contains("trạng từ") == true -> "(adv)"
            else -> "(n)"
        }
    }

    fun getTypeColor(type: String): Int {
        return when (type) {
            "(v)" -> R.color.red
            "(n)" -> R.color.green_text
            "(adj)" -> R.color.violet
            "(adv)" -> R.color.yellow
            else -> R.color.green_text
        }
    }
}