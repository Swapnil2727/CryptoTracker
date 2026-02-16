package com.app.presentation.viewmodel.cryptolist

import app.cash.turbine.test
import com.app.domain.model.CryptoCurrency
import com.app.domain.model.Result
import com.app.domain.repository.CryptoRepository
import com.app.presentation.model.CryptoListState
import com.app.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class CryptoListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: CryptoRepository = mockk()
    private lateinit var viewModel: CryptoListViewModel

    @Test
    fun `When ViewModel is initialized, Should fetch cryptos successfully`() = runTest {
        coEvery { repository.getTopCryptoCurrencies(any()) } returns flowOf(
            Result.Loading,
            Result.Success(mockData)
        )
        viewModel = CryptoListViewModel(repository)

        viewModel.state.test {
            assertEquals(CryptoListState.Loading, awaitItem())
            val success = awaitItem() as CryptoListState.Success
            assertEquals(mockData, success.cryptos)
        }
    }

    @Test
    fun `init should emit error state when initial fetch fails`() = runTest {
        // Arrange
        val errorMessage = "Network Error: Could not connect"
        val networkError = IOException(errorMessage)
        coEvery { repository.getTopCryptoCurrencies(any()) } returns flowOf(
            Result.Loading,
            Result.Error(networkError)
        )

        // Act: Initialize the ViewModel, which triggers the data fetch
        viewModel = CryptoListViewModel(repository)

        // Assert: Use Turbine to test the flow of states
        viewModel.state.test {
            // The first state emitted should be Loading
            assertEquals(CryptoListState.Loading, awaitItem())

            // The next state should be Error
            val errorState = awaitItem() as CryptoListState.Error
            assertEquals(errorMessage, errorState.message)

            // Ensure no other states are emitted
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private val mockData = listOf(
    CryptoCurrency(
        id = "bitcoin",
        symbol = "btc",
        name = "Bitcoin",
        image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
        currentPrice = 68000.50,
        priceChangePercentage24h = 1.5,
        marketCap =  1397360725394.0,
        marketCapRank = 1,
        totalVolume = 50000000000.0,
        high24h = 69000.0,
        low24h = 67000.0,
        priceChange24h = 1000.0,
        marketCapChange24h = 20000000000.0,
        marketCapChangePercentage24h = 1.55,
        circulatingSupply = 19700000.0,
        totalSupply = 21000000.0,
        maxSupply = 21000000.0,
        ath = 73750.0,
        athChangePercentage = -7.8,
        athDate = "2024-03-14T00:00:00.000Z",
        atl = 67.81,
        atlChangePercentage = 100194.5,
        atlDate = "2013-07-06T00:00:00.000Z",
        lastUpdated = "2024-05-20T10:00:00.000Z"
    ),
    CryptoCurrency(
        id = "tether",
        symbol = "usdt",
        name = "Tether",
        image = "https://assets.coingecko.com/coins/images/325/large/tether.png",
        currentPrice = 0.9998,
        priceChangePercentage24h = 0.01,
        marketCap = 110000000000.0,
        marketCapRank = 3,
        totalVolume = 90000000000.0,
        high24h = 1.001,
        low24h = 0.9995,
        priceChange24h = 0.0001,
        marketCapChange24h = 10000000.0,
        marketCapChangePercentage24h = 0.01,
        circulatingSupply = 110000000000.0,
        totalSupply = 110000000000.0,
        maxSupply = null,
        ath = 1.32,
        athChangePercentage = -24.5,
        athDate = "2018-07-24T00:00:00.000Z",
        atl = 0.57,
        atlChangePercentage = 75.4,
        atlDate = "2015-03-02T00:00:00.000Z",
        lastUpdated = "2024-05-20T10:00:02.000Z"
    ),
    CryptoCurrency(
        id = "ethereum",
        symbol = "eth",
        name = "Ethereum",
        image = "https://assets.coingecko.com/coins/images/279/large/ethereum.png",
        currentPrice = 3800.75,
        priceChangePercentage24h = -0.5,
        marketCap = 450000000000.0,
        marketCapRank = 2,
        totalVolume = 25000000000.0,
        high24h = 3850.0,
        low24h = 3750.0,
        priceChange24h = -20.0,
        marketCapChange24h = -2000000000.0,
        marketCapChangePercentage24h = -0.45,
        circulatingSupply = 120000000.0,
        totalSupply = 120000000.0,
        maxSupply = null,
        ath = 4878.26,
        athChangePercentage = -22.1,
        athDate = "2021-11-10T00:00:00.000Z",
        atl = 0.43,
        atlChangePercentage = 883720.0,
        atlDate = "2015-10-20T00:00:00.000Z",
        lastUpdated = "2024-05-20T10:00:01.000Z"
    ),
)
