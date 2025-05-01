package com.ricotronics.noaddict.data

import kotlinx.coroutines.flow.Flow

interface StreakRepository {
    suspend fun getLatestStreakDate(): StreakData?

    suspend fun addStreakDate(data: StreakData)

    suspend fun deleteAllStreaks()

    fun getAllStreakDates(): Flow<List<StreakData>>
}