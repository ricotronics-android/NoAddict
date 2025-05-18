package com.ricotronics.noaddict.data

import kotlinx.coroutines.flow.Flow

class MetaRepositoryImpl(
    private val dao: MetaDao
): MetaRepository  {
    override fun getMetaData(): Flow<List<MetaData>> {
        return dao.getMetaData()
    }

    override suspend fun addMetaData(data: MetaData) {
       dao.addMetaData(data)
    }

    override suspend fun deleteMetaData() {
        dao.deleteMetaData()
    }


}