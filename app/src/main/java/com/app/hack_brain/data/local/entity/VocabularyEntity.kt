package com.app.hack_brain.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "dict_en_vi", indices = [Index(value = ["id"], unique = true)])
data class VocabularyEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id") val id: Int,
    @NotNull
    @ColumnInfo(name = "word") val word: String,
    @NotNull
    @ColumnInfo(name = "phonetic") val phonetic: String,
    @NotNull
    @ColumnInfo(name = "meanings") val meanings: String,
    @NotNull
    @ColumnInfo(name = "note") val note: String,
    @NotNull
    @ColumnInfo(name = "is_favourite") var isFavourite: Int,
    @NotNull
    @ColumnInfo(name = "is_learn_va") var isLearnedVieEng: Int,
    @NotNull
    @ColumnInfo(name = "is_learn_av") var isLearnedEngVie: Int,
    @NotNull
    @ColumnInfo(name = "is_learn_at") var isLearnedSound: Int,
    @NotNull
    @ColumnInfo(name = "short_mean") var shortMean: String
)