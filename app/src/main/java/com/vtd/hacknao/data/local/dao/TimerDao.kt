package com.vtd.hacknao.data.local.dao

import androidx.room.*
import com.vtd.hacknao.data.local.entity.TimerEntity

@Dao
interface TimerDao {
    @Query("SELECT * FROM pushlocal")
    fun getAll(): List<TimerEntity>

    @Insert
    fun insertTimer(timer: TimerEntity)

    @Insert
    fun insertTimer(timer: List<TimerEntity>)

    @Update
    fun updateTimer(timer: TimerEntity)

    @Delete
    fun deleteTimer(timer: TimerEntity)
}