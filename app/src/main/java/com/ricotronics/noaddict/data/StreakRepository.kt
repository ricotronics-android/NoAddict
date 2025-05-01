package com.ricotronics.noaddict.data

import kotlinx.coroutines.flow.Flow

interface StreakRepository {
    suspend fun getLatestStreakDate(): StreakData?

    suspend fun addStreakDate(data: StreakData)

    fun getAllStreakDates(): Flow<List<StreakData>>
}