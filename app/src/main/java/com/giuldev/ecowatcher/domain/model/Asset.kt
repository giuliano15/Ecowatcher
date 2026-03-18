package com.giuldev.ecowatcher.domain.model

/**
 * Modelo de domínio que representa um ativo financeiro (Criptomoeda/Ação).
 * Esta classe isola a camada de domínio das mudanças estruturais da API externa.
 */
data class Asset(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val currentPrice: Double,
    val priceChangePercentage24h: Double
)
