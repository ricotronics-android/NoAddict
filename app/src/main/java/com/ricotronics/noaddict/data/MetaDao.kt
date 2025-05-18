package com.ricotronics.noaddict.data

import androidx.room.Dao;
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ricotronics.noaddict.ui.streak.MetaDataState
import kotlinx.coroutines.flow.Flow

@Dao
interface MetaDao {
    @Query("SELECT * FROM MetaData where id = 0;")
    fun getMetaData(): Flow<List<MetaData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMetaData(data: MetaData)

    @Query("DELETE FROM MetaData")
    suspend fun deleteMetaData()

}