package com.giuldev.ecowatcher.di

import com.giuldev.ecowatcher.data.local.dao.AssetDao
import com.giuldev.ecowatcher.data.remote.api.CoinGeckoApi
import com.giuldev.ecowatcher.data.repository.AssetRepositoryImpl
import com.giuldev.ecowatcher.domain.repository.AssetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para injeção do Repositório.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAssetRepository(
        api: CoinGeckoApi,
        dao: AssetDao
    ): AssetRepository {
        return AssetRepositoryImpl(api, dao)
    }
}
