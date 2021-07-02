package com.app.hack_brain.data.local.dao

import androidx.room.*
import com.app.hack_brain.data.local.entity.VocabularyEntity

@Dao
interface VocabularyDao {
    @Query("SELECT * FROM dict_en_vi ORDER BY LENGTH(word) LIMIT :amount OFFSET :amount*:unit")
    fun getVocabularyOfUnit(amount: Int, unit: Int): List<VocabularyEntity>

    @Query("SELECT * FROM dict_en_vi ORDER BY RANDOM() LIMIT 5")
    fun getRandomVocabulary(): List<VocabularyEntity>

    @Query("UPDATE dict_en_vi SET is_favorite=:isFavourite WHERE id=:id")
    fun setFavouriteVoc(id: Int, isFavourite: Int)

    @Query("SELECT * FROM dict_en_vi WHERE is_favorite=1")
    fun getFavouriteVocList(): List<VocabularyEntity>

    @Update
    fun updateVocabulary(vocabulary: VocabularyEntity)

    @Insert
    fun insertVocabulary(vocabulary: VocabularyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVocabularyList(vocabulary: List<VocabularyEntity>)

}