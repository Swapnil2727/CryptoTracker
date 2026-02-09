package com.app.domain.repository

import com.app.domain.model.CryptoCurrency
import com.app.domain.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for accessing cryptocurrency data.
 * The implementation of this interface will be in the data layer.
 */
interface CryptoRepository {

    /**
     * Fetches a list of top cryptocurrencies.
     * It uses a single source of truth strategy, fetching from the network and caching locally.
     *
     * @param forceRefresh If true, forces a fetch from the network, ignoring the cache.
     * @return A Flow emitting the result of the operation, starting with Loading, then either Success or Error.
     */
    fun getTopCryptoCurrencies(forceRefresh: Boolean = false): Flow<Result<List<CryptoCurrency>>>

    /**
     * Retrieves a single cryptocurrency by its unique identifier from the local cache.
     *
     * @param id The unique ID of the cryptocurrency (e.g., "bitcoin").
     * @return A Flow emitting the result, containing the found cryptocurrency or an error if not found.
     */
    fun getCryptoCurrencyById(id: String): Flow<Result<CryptoCurrency>>

    /**
     * Forces a refresh of the cryptocurrency list from the remote API and updates the local cache.
     * This is a suspend function as it's a one-shot operation.
     */
    suspend fun refreshCryptoCurrencies()
}
