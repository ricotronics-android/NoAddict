package com.ricotronics.noaddict.data

import kotlinx.coroutines.flow.Flow

interface MetaRepository {
    fun getMetaData(): Flow<List<MetaData>>

    suspend fun addMetaData(data: MetaData)

    suspend fun deleteMetaData()
}