package com.app.presentation.viewmodel.cryptolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.Result
import com.app.domain.repository.CryptoRepository
import com.app.presentation.model.CryptoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Crypto List screen.
 *
 * Responsibilities:
 * - Fetch cryptocurrency data using use cases
 * - Manage UI state (loading, success, error)
 * - Handle user actions (refresh, retry)
 * - Survive configuration changes (screen rotation)
 */
@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository,
) : ViewModel() {

    // Private mutable state - only ViewModel can change it
    private val _state = MutableStateFlow<CryptoListState>(CryptoListState.Loading)

    // Public immutable state - UI can only observe it
    val state: StateFlow<CryptoListState> = _state.asStateFlow()

    init {
        // Load data when ViewModel is created
        loadCryptoCurrencies()
    }

    /**
     * Load cryptocurrencies from repository.
     * This will first emit cached data (if available), then fetch fresh data.
     */
    private fun loadCryptoCurrencies(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            cryptoRepository.getTopCryptoCurrencies(forceRefresh).collect { result ->
                _state.value = when (result) {
                    is Result.Loading -> {
                        // Show loading only if we don't have data yet
                        if (_state.value is CryptoListState.Success) {
                            (_state.value as CryptoListState.Success).copy(isRefreshing = true)
                        } else {
                            CryptoListState.Loading
                        }
                    }

                    is Result.Success -> {
                        CryptoListState.Success(
                            cryptos = result.data,
                            isRefreshing = false
                        )
                    }

                    is Result.Error -> {
                        // If we have cached data, keep showing it with error toast
                        // Otherwise show error screen
                        if (_state.value is CryptoListState.Success) {
                            (_state.value as CryptoListState.Success).copy(isRefreshing = false)
                        } else {
                            CryptoListState.Error(
                                message = result.exception.message ?: "Unknown error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Called when user pulls to refresh.
     * Forces a network call to get fresh data.
     */
    fun onRefresh() {
        loadCryptoCurrencies(forceRefresh = true)
    }

    /**
     * Called when user taps retry button on error screen.
     */
    fun onRetry() {
        loadCryptoCurrencies(forceRefresh = true)
    }
}