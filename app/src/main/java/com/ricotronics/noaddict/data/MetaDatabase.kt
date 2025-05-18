package com.ricotronics.noaddict.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MetaData::class],
    version = 1
)
abstract class MetaDatabase: RoomDatabase()  {
    abstract val dao: MetaDao
}