package com.app.data.mappers

import com.app.data.local.CryptoCurrencyEntity
import com.app.data.remote.dto.CryptoDTO
import com.app.domain.model.CryptoCurrency

fun CryptoDTO.toEntity() = CryptoCurrencyEntity(
    id = id,
    symbol = symbol,
    name = name,
    image = image,
    currentPrice = currentPrice ?: 0.0,
    priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
    marketCap = marketCap ?: 0.0,
    marketCapRank = marketCapRank ?: 0,
    totalVolume = totalVolume ?: 0.0,
    high24h = high24h ?: 0.0,
    low24h = low24h ?: 0.0,
    priceChange24h = priceChange24h ?: 0.0,
    marketCapChange24h = marketCapChange24h ?: 0.0,
    marketCapChangePercentage24h = marketCapChangePercentage24h ?: 0.0,
    circulatingSupply = circulatingSupply ?: 0.0,
    totalSupply = totalSupply,
    maxSupply = maxSupply,
    ath = ath ?: 0.0,
    athChangePercentage = athChangePercentage ?: 0.0,
    athDate = athDate ?: "",
    atl = atl ?: 0.0,
    atlChangePercentage = atlChangePercentage ?: 0.0,
    atlDate = atlDate ?: "",
    lastUpdated = lastUpdated ?: ""
)

fun CryptoCurrencyEntity.toDomain() = CryptoCurrency(
    id = id,
    symbol = symbol,
    name = name,
    image = image,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    marketCap = marketCap,
    marketCapRank = marketCapRank,
    totalVolume = totalVolume,
    high24h = high24h,
    low24h = low24h,
    priceChange24h = priceChange24h,
    marketCapChange24h = marketCapChange24h,
    marketCapChangePercentage24h = marketCapChangePercentage24h,
    circulatingSupply = circulatingSupply,
    totalSupply = totalSupply,
    maxSupply = maxSupply,
    ath = ath,
    athChangePercentage = athChangePercentage,
    athDate = athDate,
    atl = atl,
    atlChangePercentage = atlChangePercentage,
    atlDate = atlDate,
    lastUpdated = lastUpdated
)

fun List<CryptoCurrencyEntity>.toDomain(): List<CryptoCurrency> {
    return map { it.toDomain() }
}