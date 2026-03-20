package com.giuldev.ecowatcher.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para a resposta da API da CoinGecko.
 * Mapeia os campos do JSON para as propriedades do Kotlin.
 */
data class AssetResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("current_price") val currentPrice: Double?,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double?,
    @SerializedName("market_cap") val marketCap: Double?,
    @SerializedName("total_volume") val totalVolume: Double?,
    @SerializedName("high_24h") val high24h: Double?,
    @SerializedName("low_24h") val low24h: Double?
)
