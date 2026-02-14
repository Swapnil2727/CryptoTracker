package com.app.presentation.ui.util

import com.app.domain.model.CryptoCurrency

/**
 * Preview data for Bitcoin with positive price change
 */
internal val previewBitcoin = CryptoCurrency(
    id = "bitcoin",
    symbol = "btc",
    name = "Bitcoin",
    image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png",
    currentPrice = 70083.0,
    priceChangePercentage24h = 9.44,
    marketCap = 1397360725394.0,
    marketCapRank = 1,
    totalVolume = 110266078010.0,
    high24h = 71604.0,
    low24h = 63924.0,
    priceChange24h = 6046.17,
    marketCapChange24h = 7895.66,
    marketCapChangePercentage24h = 9.10705,
    circulatingSupply = 19985371.0,
    totalSupply = 19985371.0,
    maxSupply = 21000000.0,
    ath = 126080.0,
    athChangePercentage = -44.41378,
    athDate = "2025-10-06T18:57:42.558Z",
    atl = 67.81,
    atlChangePercentage = 103253.60534,
    atlDate = "2013-07-06T00:00:00.000Z",
    lastUpdated = "2026-02-07T01:28:59.733Z"
)

/**
 * Preview data for Ethereum with negative price change
 */
internal val previewEthereumNegative = CryptoCurrency(
    id = "ethereum",
    symbol = "eth",
    name = "Ethereum",
    image = "https://coin-images.coingecko.com/coins/images/279/large/ethereum.png",
    currentPrice = 2047.03,
    priceChangePercentage24h = -5.23,
    marketCap = 246284217655.0,
    marketCapRank = 2,
    totalVolume = 53838223165.0,
    high24h = 2184.66,
    low24h = 2020.4,
    priceChange24h = -113.42,
    marketCapChange24h = -7895.66,
    marketCapChangePercentage24h = -5.26,
    circulatingSupply = 120692654.858621,
    totalSupply = 120692919.179321,
    maxSupply = null,
    ath = 4946.05,
    athChangePercentage = -58.61275,
    athDate = "2025-08-24T19:21:03.333Z",
    atl = 0.432979,
    atlChangePercentage = 472679.05623,
    atlDate = "2015-10-20T00:00:00.000Z",
    lastUpdated = "2026-02-07T01:29:00.031Z"
)

/**
 * Preview data for a low-price cryptocurrency
 */
internal val previewLowPriceCoin = CryptoCurrency(
    id = "shiba-inu",
    symbol = "shib",
    name = "Shiba Inu",
    image = "https://coin-images.coingecko.com/coins/images/11939/large/shiba.png",
    currentPrice = 0.00000625,
    priceChangePercentage24h = 9.21,
    marketCap = 3678369193.0,
    marketCapRank = 30,
    totalVolume = 229042275.0,
    high24h = 0.0000064,
    low24h = 0.0000057,
    priceChange24h = 0.00000053,
    marketCapChange24h =  7895.66,
    marketCapChangePercentage24h = 9.05388,
    circulatingSupply = 589243888988152.0,
    totalSupply = 589500423988428.0,
    maxSupply = null,
    ath = 0.00008616,
    athChangePercentage = -92.74957,
    athDate = "2021-10-28T03:54:55.568Z",
    atl = 5.6366e-11,
    atlChangePercentage = 11082537.74804,
    atlDate = "2020-11-28T11:26:25.838Z",
    lastUpdated = "2026-02-07T01:29:00.826Z"
)

/**
 * Preview data for a list of cryptocurrencies
 */
internal val previewCryptoList = listOf(
    previewBitcoin,
    previewEthereumNegative,
    CryptoCurrency(
        id = "tether",
        symbol = "usdt",
        name = "Tether",
        image = "https://coin-images.coingecko.com/coins/images/325/large/Tether.png",
        currentPrice = 0.999603,
        priceChangePercentage24h = 0.09,
        marketCap = 185638317770.0,
        marketCapRank = 3,
        totalVolume = 183527367665.0,
        high24h = 0.999871,
        low24h = 0.998617,
        priceChange24h = 0.00095184,
        marketCapChange24h = 17245.4149,
        marketCapChangePercentage24h = 0.09298,
        circulatingSupply = 185716038614.827,
        totalSupply = 191182975698.136,
        maxSupply = null,
        ath = 1.32,
        athChangePercentage = -24.44929,
        athDate = "2018-07-24T00:00:00.000Z",
        atl = 0.572521,
        atlChangePercentage = 74.5976,
        atlDate = "2015-03-02T00:00:00.000Z",
        lastUpdated = "2026-02-07T01:29:03.197Z"
    ),
    CryptoCurrency(
        id = "binancecoin",
        symbol = "bnb",
        name = "BNB",
        image = "https://coin-images.coingecko.com/coins/images/825/large/bnb-icon2_2x.png",
        currentPrice = 652.74,
        priceChangePercentage24h = 4.93,
        marketCap = 88796043494.0,
        marketCapRank = 4,
        totalVolume = 2557758593.0,
        high24h = 667.0,
        low24h = 613.32,
        priceChange24h = 30.7,
        marketCapChange24h = 40868.03163,
        marketCapChangePercentage24h = 4.82451,
        circulatingSupply = 136359676.58,
        totalSupply = 136359676.58,
        maxSupply = 200000000.0,
        ath = 1369.99,
        athChangePercentage = -52.35427,
        athDate = "2025-10-13T08:41:24.131Z",
        atl = 0.0398177,
        atlChangePercentage = 1639227.67046,
        atlDate = "2017-10-19T00:00:00.000Z",
        lastUpdated = "2026-02-07T01:28:59.439Z"
    ),
    previewLowPriceCoin
)