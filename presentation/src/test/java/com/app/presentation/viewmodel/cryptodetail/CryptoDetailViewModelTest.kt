package com.app.presentation.viewmodel.cryptodetail

import app.cash.turbine.test
import com.app.domain.model.CryptoCurrency
import com.app.domain.model.Result
import com.app.domain.repository.CryptoRepository
import com.app.presentation.model.CryptoDetailState
import com.app.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class CryptoDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: CryptoRepository = mockk()
    private lateinit var viewModel: CryptoDetailViewModel

    @Test
    fun `init should emit loading then success when repository returns data`() = runTest {
        coEvery { repository.getCryptoCurrencyById("bitcoin") } returns flowOf(
            Result.Loading,
            Result.Success(mockBitcoin)
        )

        viewModel = CryptoDetailViewModel("bitcoin", repository)

        viewModel.state.test {
            assertEquals(CryptoDetailState.Loading, awaitItem())
            val success = awaitItem() as CryptoDetailState.Success
            assertEquals(mockBitcoin, success.crypto)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init should emit loading then error when repository fails`() = runTest {
        val errorMessage = "Network Error: Could not connect"
        coEvery { repository.getCryptoCurrencyById("bitcoin") } returns flowOf(
            Result.Loading,
            Result.Error(IOException(errorMessage))
        )

        viewModel = CryptoDetailViewModel("bitcoin", repository)

        viewModel.state.test {
            assertEquals(CryptoDetailState.Loading, awaitItem())
            val error = awaitItem() as CryptoDetailState.Error
            assertEquals(errorMessage, error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `error state should use fallback message when exception has no message`() = runTest {
        coEvery { repository.getCryptoCurrencyById("bitcoin") } returns flowOf(
            Result.Loading,
            Result.Error(IOException())
        )

        viewModel = CryptoDetailViewModel("bitcoin", repository)

        viewModel.state.test {
            assertEquals(CryptoDetailState.Loading, awaitItem())
            val error = awaitItem() as CryptoDetailState.Error
            assertEquals("Unknown error occurred", error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onRetry should reload data after error`() = runTest {
        // First call fails
        coEvery { repository.getCryptoCurrencyById("bitcoin") } returns flowOf(
            Result.Loading,
            Result.Error(IOException("Network error"))
        )

        viewModel = CryptoDetailViewModel("bitcoin", repository)

        viewModel.state.test {
            assertEquals(CryptoDetailState.Loading, awaitItem())
            assertEquals("Network error", (awaitItem() as CryptoDetailState.Error).message)

            // Second call succeeds
            coEvery { repository.getCryptoCurrencyById("bitcoin") } returns flowOf(
                Result.Loading,
                Result.Success(mockBitcoin)
            )

            viewModel.onRetry()

            assertEquals(CryptoDetailState.Loading, awaitItem())
            val success = awaitItem() as CryptoDetailState.Success
            assertEquals(mockBitcoin, success.crypto)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `cryptoId should match the constructor parameter`() = runTest {
        coEvery { repository.getCryptoCurrencyById("ethereum") } returns flowOf(
            Result.Loading,
            Result.Success(mockBitcoin)
        )

        viewModel = CryptoDetailViewModel("ethereum", repository)

        assertEquals("ethereu", viewModel.cryptoId)
    }
}

private val mockBitcoin = CryptoCurrency(
    id = "bitcoin",
    symbol = "btc",
    name = "Bitcoin",
    image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
    currentPrice = 68000.50,
    priceChangePercentage24h = 1.5,
    marketCap = 1397360725394.0,
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
)
