package com.giuldev.ecowatcher.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room que representa a tabela de ativos (Assets) no banco de dados local.
 * Usado para armazenar dados em cache e oferecer suporte à funcionalidade offline-first.
 */
@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val currentPrice: Double,
    val priceChangePercentage24h: Double
)
