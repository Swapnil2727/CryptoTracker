package com.app.data.remote.api

import com.app.data.remote.dto.CryptoDTO

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("coins/markets")
    suspend fun getCryptoMarkets(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("price_change_percentage") priceChangePercentage: String = "24h"
    ): List<CryptoDTO>

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}