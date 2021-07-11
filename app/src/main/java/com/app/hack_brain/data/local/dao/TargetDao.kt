package com.app.hack_brain.data.local.dao

import androidx.room.*
import com.app.hack_brain.data.local.entity.TargetEntity

@Dao
interface TargetDao {
    @Query("SELECT * FROM muctieu")
    fun getAll(): List<TargetEntity>

    @Query("SELECT * FROM muctieu WHERE (date = :date AND status = 1) LIMIT 1")
    fun getTargetEntity(date: String): TargetEntity

    @Query("SELECT * FROM muctieu LIMIT 1")
    fun getFirstTarget(): TargetEntity

    @Insert
    fun insertTarget(vararg target: TargetEntity)

    @Update
    fun updateTarget(target: TargetEntity)

    @Delete
    fun deleteTarget(target: TargetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTargetList(target: List<TargetEntity>)
}