package com.giuldev.ecowatcher.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Modelo de domínio que representa um ativo financeiro (Criptomoeda/Ação).
 * Esta classe isola a camada de domínio das mudanças estruturais da API externa.
 */
@Parcelize
data class Asset(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val currentPrice: Double,
    val priceChangePercentage24h: Double,
    val marketCap: Double,
    val totalVolume: Double,
    val high24h: Double,
    val low24h: Double
) : Parcelable
