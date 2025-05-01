package com.ricotronics.noaddict.data

import kotlinx.coroutines.flow.Flow

class StreakRepositoryImpl(
    private val dao: SteakDao
): StreakRepository  {
    override suspend fun getLatestStreakDate(): StreakData? {
        return dao.getLatestStreakDate()
    }

    override suspend fun addStreakDate(data: StreakData) {
        dao.addStreakDate(data)
    }

    override suspend fun deleteAllStreaks() {
        dao.deleteAllStreaks()
    }

    override fun getAllStreakDates(): Flow<List<StreakData>> {
        return dao.getAllStreakDates()
    }

}