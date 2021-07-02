package com.app.hack_brain.data.local.dao

import androidx.room.*
import com.app.hack_brain.data.local.entity.UnitEntity

@Dao
interface UnitDao {
    @Query("SELECT * FROM unit_tracking ORDER BY unit")
    fun getUnitList(): List<UnitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnit(unit: UnitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnitList(unit: List<UnitEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUnit(unit: UnitEntity)

    @Query("UPDATE unit_tracking SET progress_av=:progress WHERE unit=:unit")
    fun updateEngVieProgress(unit: Int, progress: Int)

    @Query("UPDATE unit_tracking SET progress_va=:progress WHERE unit=:unit")
    fun updateVieEngProgress(unit: Int, progress: Int)

    @Query("UPDATE unit_tracking SET progress_at=:progress WHERE unit=:unit")
    fun updateSoundProgress(unit: Int, progress: Int)
}