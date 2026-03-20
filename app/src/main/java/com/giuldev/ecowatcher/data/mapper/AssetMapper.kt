package com.giuldev.ecowatcher.data.mapper

import com.giuldev.ecowatcher.data.local.entity.AssetEntity
import com.giuldev.ecowatcher.data.remote.model.AssetResponse
import com.giuldev.ecowatcher.domain.model.Asset

/**
 * Funções de extensão (Mapper) para conversão entre camadas (Data/Local/Remote -> Domain).
 * Garante que a camada de Domínio seja completamente independente da API e do Room.
 */

fun AssetResponse.toEntity(): AssetEntity {
    return AssetEntity(
        id = this.id.orEmpty(),
        symbol = this.symbol?.uppercase().orEmpty(),
        name = this.name.orEmpty(),
        imageUrl = this.image.orEmpty(),
        currentPrice = this.currentPrice ?: 0.0,
        priceChangePercentage24h = this.priceChangePercentage24h ?: 0.0,
        marketCap = this.marketCap ?: 0.0,
        totalVolume = this.totalVolume ?: 0.0,
        high24h = this.high24h ?: 0.0,
        low24h = this.low24h ?: 0.0
    )
}

fun List<AssetResponse>.toEntityList(): List<AssetEntity> {
    return this.map { it.toEntity() }
}

fun AssetEntity.toDomain(): Asset {
    return Asset(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        imageUrl = this.imageUrl,
        currentPrice = this.currentPrice,
        priceChangePercentage24h = this.priceChangePercentage24h,
        marketCap = this.marketCap,
        totalVolume = this.totalVolume,
        high24h = this.high24h,
        low24h = this.low24h
    )
}

fun List<AssetEntity>.toDomainList(): List<Asset> {
    return this.map { it.toDomain() }
}
