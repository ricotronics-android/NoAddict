package com.ricotronics.noaddict.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StreakData::class],
    version = 1
)
abstract class StreakDatabase: RoomDatabase()  {
    abstract val dao: SteakDao
}