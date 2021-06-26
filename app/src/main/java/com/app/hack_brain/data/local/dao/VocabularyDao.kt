package com.app.hack_brain.data.local.dao

import androidx.room.*
import com.app.hack_brain.data.local.entity.VocabularyEntity

@Dao
interface VocabularyDao {
    @Query("SELECT * FROM dict_en_vi ORDER BY LENGTH(word) LIMIT :amount OFFSET :amount*:unit")
    fun getAll(amount: Int, unit: Int): List<VocabularyEntity>

    @Query("SELECT * FROM dict_en_vi")
    fun findVocabulary(): VocabularyEntity

    @Update
    fun updateVocabulary(vocabulary: VocabularyEntity)

    @Insert
    fun insertVocabulary(vocabulary: VocabularyEntity)

}