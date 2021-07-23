package com.app.hack_brain.data.local.dao

import androidx.room.*
import com.app.hack_brain.data.local.entity.TargetEntity

@Dao
interface TargetDao {
    @Query("SELECT * FROM muctieu")
    fun getAll(): List<TargetEntity>

    @Query("SELECT * FROM muctieu WHERE (date = :date AND status = 1) LIMIT 1")
    fun getTargetEntity(date: String): TargetEntity

    @Query("SELECT * FROM muctieu WHERE (date = :date AND status = 1)")
    fun getTargetListOfDay(date: String): List<TargetEntity>

    @Query("UPDATE muctieu SET status=:status WHERE unit=:unit")
    fun updateTargetByUnit(unit: Int, status: Int)

    @Insert
    fun insertTarget(vararg target: TargetEntity)

    @Update
    fun updateTarget(target: TargetEntity)

    @Delete
    fun deleteTarget(target: TargetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTargetList(target: List<TargetEntity>)
}