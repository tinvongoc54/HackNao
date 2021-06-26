package com.app.hack_brain.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Entity(tableName = "dict_en_vi")
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
)