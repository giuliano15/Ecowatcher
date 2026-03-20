package com.giuldev.ecowatcher.domain.usecase

import com.giuldev.ecowatcher.domain.model.Asset
import com.giuldev.ecowatcher.domain.repository.AssetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para busca de ativos por nome ou símbolo.
 */
interface SearchAssetsUseCase {
    operator fun invoke(query: String): Flow<List<Asset>>
}

class SearchAssetsUseCaseImpl @Inject constructor(
    private val repository: AssetRepository
) : SearchAssetsUseCase {
    override operator fun invoke(query: String): Flow<List<Asset>> {
        return repository.searchAssets(query)
    }
}
