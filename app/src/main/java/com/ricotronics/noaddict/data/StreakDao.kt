package com.ricotronics.noaddict.data

import androidx.room.Dao;
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SteakDao {
    @Query("SELECT id, MAX(startDate) as startDate FROM StreakData GROUP BY id;")
    suspend fun getLatestStreakDate(): StreakData?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStreakDate(data: StreakData)

    @Query("DELETE FROM StreakData")
    suspend fun deleteAllStreaks()

    @Query("SELECT * from StreakData")
    fun getAllStreakDates(): Flow<List<StreakData>>
}