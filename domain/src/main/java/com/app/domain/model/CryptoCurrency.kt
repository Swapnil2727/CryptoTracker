package com.app.domain.model

data class CryptoCurrency(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val priceChangePercentage24h: Double,

    // Detail screen fields
    val marketCap: Double,
    val marketCapRank: Int,
    val totalVolume: Double,
    val high24h: Double,
    val low24h: Double,
    val priceChange24h: Double,
    val marketCapChange24h: Double,
    val marketCapChangePercentage24h: Double,
    val circulatingSupply: Double,
    val totalSupply: Double?,
    val maxSupply: Double?,
    val ath: Double,
    val athChangePercentage: Double,
    val athDate: String,
    val atl: Double,
    val atlChangePercentage: Double,
    val atlDate: String,
    val lastUpdated: String
)