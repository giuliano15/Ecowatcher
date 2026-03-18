package com.giuldev.ecowatcher.data.remote.api

import com.giuldev.ecowatcher.data.remote.model.AssetResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface Retrofit para a API da CoinGecko.
 * Define os endpoints necessários para buscar os dados de mercado.
 */
interface CoinGeckoApi {

    @GET("coins/markets")
    suspend fun getMarkets(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): List<AssetResponse>

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}
