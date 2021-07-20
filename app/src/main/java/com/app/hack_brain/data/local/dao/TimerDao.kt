package com.app.hack_brain.data.local.dao

import androidx.room.*
import com.app.hack_brain.data.local.entity.TimerEntity

@Dao
interface TimerDao {
    @Query("SELECT * FROM pushlocal")
    fun getAll(): List<TimerEntity>

    @Insert
    fun insertTimer(timer: List<TimerEntity>)

    @Update
    fun updateTimer(timer: TimerEntity)

    @Delete
    fun deleteTimer(timer: TimerEntity)
}