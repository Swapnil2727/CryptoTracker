package com.app.presentation.model

import com.app.domain.model.CryptoCurrency

/**
 * Represents the state of the Crypto Detail screen.
 */
sealed interface CryptoDetailState {
    object Loading : CryptoDetailState

    data class Success(val crypto: CryptoCurrency) : CryptoDetailState

    data class Error(val message: String) : CryptoDetailState
}