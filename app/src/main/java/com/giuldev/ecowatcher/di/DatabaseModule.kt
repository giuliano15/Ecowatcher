package com.giuldev.ecowatcher.di

import android.content.Context
import androidx.room.Room
import com.giuldev.ecowatcher.data.local.EcoWatcherDatabase
import com.giuldev.ecowatcher.data.local.dao.AssetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para injeção de dependências do Room Database e DAOs.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EcoWatcherDatabase {
        return Room.databaseBuilder(
            context,
            EcoWatcherDatabase::class.java,
            "eco_watcher_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAssetDao(database: EcoWatcherDatabase): AssetDao {
        return database.assetDao()
    }
}
