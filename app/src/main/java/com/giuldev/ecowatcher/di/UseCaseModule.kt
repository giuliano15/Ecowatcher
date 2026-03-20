package com.giuldev.ecowatcher.di

import com.giuldev.ecowatcher.domain.usecase.GetMarketAssetsUseCase
import com.giuldev.ecowatcher.domain.usecase.GetMarketAssetsUseCaseImpl
import com.giuldev.ecowatcher.domain.usecase.SearchAssetsUseCase
import com.giuldev.ecowatcher.domain.usecase.SearchAssetsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Módulo Hilt para injeção dos Casos de Uso (UseCases).
 * Ligamos as interfaces com as implementações.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetMarketAssetsUseCase(
        impl: GetMarketAssetsUseCaseImpl
    ): GetMarketAssetsUseCase

    @Binds
    abstract fun bindSearchAssetsUseCase(
        impl: SearchAssetsUseCaseImpl
    ): SearchAssetsUseCase
}
