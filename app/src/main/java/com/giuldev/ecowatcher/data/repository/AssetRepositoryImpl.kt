package com.giuldev.ecowatcher.data.repository

import com.giuldev.ecowatcher.data.local.dao.AssetDao
import com.giuldev.ecowatcher.data.mapper.toDomainList
import com.giuldev.ecowatcher.data.mapper.toEntityList
import com.giuldev.ecowatcher.data.remote.api.CoinGeckoApi
import com.giuldev.ecowatcher.domain.model.Asset
import com.giuldev.ecowatcher.domain.model.Resource
import com.giuldev.ecowatcher.domain.repository.AssetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Implementação do [AssetRepository] que utiliza a estratégia Offline-First.
 * Fluxo de Ouro:
 * 1. Emite estado de Loading.
 * 2. Tenta buscar os dados da API remota.
 * 3. Se sucesso: Salva no Room e retorna os dados atualizados.
 * 4. Se falhar (Ex: sem internet): Busca o último cache salvo no Room.
 * 5. Se o cache existir, retorna Success com dados antigos. Se não, retorna Error.
 */
class AssetRepositoryImpl(
    private val api: CoinGeckoApi,
    private val dao: AssetDao
) : AssetRepository {

    override fun getAssets(): Flow<Resource<List<Asset>>> = flow {
        emit(Resource.Loading())

        try {
            // Tenta buscar da API
            val remoteAssets = api.getMarkets()
            
            // Mapeia para Entidade do Room e salva (substituindo antigas)
            val entities = remoteAssets.toEntityList()
            dao.insertAssets(entities)
            
            // Retorna os dados recém-salvos para o domínio
            val localAssets = dao.getAllAssets()
            emit(Resource.Success(localAssets.toDomainList()))

        } catch (e: Exception) {
            // Se falhar (ex: sem internet), tenta buscar do banco de dados local (Room)
            val localAssets = dao.getAllAssets()
            if (localAssets.isNotEmpty()) {
                emit(Resource.Success(localAssets.toDomainList()))
            } else {
                emit(Resource.Error(message = e.message ?: "Erro desconhecido ao buscar dados offline."))
            }
        }
    }
}
