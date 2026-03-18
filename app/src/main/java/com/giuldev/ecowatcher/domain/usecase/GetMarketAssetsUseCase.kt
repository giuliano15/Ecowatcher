package com.giuldev.ecowatcher.domain.usecase

import com.giuldev.ecowatcher.domain.model.Asset
import com.giuldev.ecowatcher.domain.model.Resource
import com.giuldev.ecowatcher.domain.repository.AssetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso (UseCase) responsável por obter os ativos financeiros.
 * Encapsula a lógica de negócio separando-a da ViewModel e do Repositório.
 */
interface GetMarketAssetsUseCase {
    operator fun invoke(): Flow<Resource<List<Asset>>>
}

class GetMarketAssetsUseCaseImpl @Inject constructor(
    private val repository: AssetRepository
) : GetMarketAssetsUseCase {
    override operator fun invoke(): Flow<Resource<List<Asset>>> {
        return repository.getAssets()
    }
}
