package com.app.data.mappers

import com.app.data.remote.dto.CryptoDTO
import com.app.domain.model.CryptoCurrency

fun CryptoDTO.toDomain() = CryptoCurrency(
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

fun List<CryptoDTO>.toDomain(): List<CryptoCurrency> {
    return map { it.toDomain() }
}