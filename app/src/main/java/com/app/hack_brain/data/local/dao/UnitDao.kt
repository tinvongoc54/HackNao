package com.app.hack_brain.data.local.dao

import androidx.room.*
import com.app.hack_brain.data.local.entity.UnitEntity

@Dao
interface UnitDao {
    @Query("SELECT * FROM unit_tracking ORDER BY unit")
    fun getUnitList(): List<UnitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnit(unit: UnitEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUnit(unit: UnitEntity)
}