package com.giuldev.ecowatcher.domain.repository

import com.giuldev.ecowatcher.domain.model.Asset
import com.giuldev.ecowatcher.domain.model.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Interface do repositório de ativos (Assets).
 * Define o contrato para obtenção dos dados, independentemente da fonte (Remota ou Local).
 */
interface AssetRepository {
    fun getAssets(): Flow<Resource<List<Asset>>>
}
