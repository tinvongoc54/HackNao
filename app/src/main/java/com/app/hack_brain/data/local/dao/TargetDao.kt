package com.app.hack_brain.data.local.dao

import androidx.room.*
import com.app.hack_brain.data.local.entity.TargetEntity

@Dao
interface TargetDao {
    @Query("SELECT * FROM muctieu")
    fun getAll(): List<TargetEntity>

    @Insert
    fun insertTarget(vararg target: TargetEntity)

    @Update
    fun updateTarget(target: TargetEntity)

    @Delete
    fun deleteTarget(target: TargetEntity)
}