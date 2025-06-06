package com.ricotronics.noaddict.di

import android.app.Application
import androidx.room.Room
import com.ricotronics.noaddict.data.MetaDatabase
import com.ricotronics.noaddict.data.MetaRepository
import com.ricotronics.noaddict.data.MetaRepositoryImpl
import com.ricotronics.noaddict.data.StreakDatabase
import com.ricotronics.noaddict.data.StreakRepository
import com.ricotronics.noaddict.data.StreakRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStreakDatabase(app: Application): StreakDatabase {
        return Room.databaseBuilder(
            app,
            StreakDatabase::class.java,
            name = "streak_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMetaDatabase(app: Application): MetaDatabase {
        return Room.databaseBuilder(
            app,
            MetaDatabase::class.java,
            name = "meta_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideStreakRepository(database: StreakDatabase): StreakRepository {
        return StreakRepositoryImpl(database.dao)
    }

    @Provides
    @Singleton
    fun provideMetaRepository(database: MetaDatabase): MetaRepository {
        return MetaRepositoryImpl(database.dao)
    }
}