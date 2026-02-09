package com.app.presentation.model

import com.app.domain.model.CryptoCurrency

/**
 * Represents the state of the Crypto List screen.
 *
 * This is a sealed interface because the screen can be in one of these states:
 * - Loading (showing progress indicator)
 * - Success (showing list of cryptos)
 * - Error (showing error message)
 */
sealed interface CryptoListState {
    /**
     * Initial loading state or refreshing state
     */
    object Loading : CryptoListState

    /**
     * Successfully loaded cryptocurrency data
     * @param cryptos List of cryptocurrencies to display
     * @param isRefreshing True if user triggered a manual refresh
     */
    data class Success(
        val cryptos: List<CryptoCurrency>,
        val isRefreshing: Boolean = false
    ) : CryptoListState

    /**
     * Error state when data fetching fails
     * @param message Human-readable error message
     */
    data class Error(val message: String) : CryptoListState
}