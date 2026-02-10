package com.app.presentation.viewmodel.cryptodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.Result
import com.app.domain.repository.CryptoRepository
import com.app.presentation.model.CryptoDetailState
import com.app.presentation.ui.cryptodetail.CryptoDetailDestination
import dev.enro.core.close
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Crypto Detail screen.
 *
 * Responsibilities:
 * - Fetch detailed cryptocurrency data
 * - Manage UI state (loading, success, error)
 * - Handle user actions (retry)
 * - Survive configuration changes
 */
class CryptoDetailViewModel(
    private val cryptoRepository: CryptoRepository,
) : ViewModel() {

    private val navigation by navigationHandle<CryptoDetailDestination>()

    // Private mutable state
    private val _state = MutableStateFlow<CryptoDetailState>(CryptoDetailState.Loading)

    // Public immutable state
    val state: StateFlow<CryptoDetailState> = _state.asStateFlow()

    init {
        // Load crypto details when ViewModel is created
        loadCryptoDetail()
    }

    /**
     * Load cryptocurrency details from repository
     */
    private fun loadCryptoDetail() {
        viewModelScope.launch {
            cryptoRepository.getCryptoCurrencyById(navigation.key.cryptoId).collect { result ->
                _state.value = when (result) {
                    is Result.Loading -> CryptoDetailState.Loading

                    is Result.Success -> CryptoDetailState.Success(crypto = result.data)

                    is Result.Error -> CryptoDetailState.Error(
                        message = result.exception.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    /**
     * Called when user taps retry button on error screen
     */
    fun onRetry() {
        loadCryptoDetail()
    }

    fun onBackClick() {
        navigation.close()
    }
}