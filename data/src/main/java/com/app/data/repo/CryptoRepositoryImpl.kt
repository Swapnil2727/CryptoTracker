package com.app.data.repo

import com.app.data.local.dao.CryptoDao
import com.app.data.mappers.toDomain
import com.app.data.mappers.toEntity
import com.app.data.remote.api.CoinGeckoApi
import com.app.domain.model.CryptoCurrency
import com.app.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import com.app.domain.model.Result
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApi,
    private val dao: CryptoDao
) : CryptoRepository {

    override fun getTopCryptoCurrencies(forceRefresh: Boolean): Flow<Result<List<CryptoCurrency>>> = flow {
        emit(Result.Loading)

        try {
            // First, emit cached data if available
            val cachedCount = dao.getCount()
            if (cachedCount > 0 && !forceRefresh) {
                dao.getAllCryptoCurrencies().map { entities ->
                    Result.Success(entities.toDomain())
                }.collect { emit(it) }
            }

            // Then fetch fresh data from API
            if (forceRefresh || cachedCount == 0) {
                val response = api.getCryptoMarkets()
                val entities = response.map { it.toEntity() }
                dao.deleteAll()
                dao.insertAll(entities)

                // Emit fresh data
                emit(Result.Success(response.toDomain()))
            }
        } catch (e: Exception) {
            // If network fails, try to emit cached data
            val cachedCount = dao.getCount()
            if (cachedCount > 0) {
                dao.getAllCryptoCurrencies().map { entities ->
                    Result.Success(entities.toDomain())
                }.collect { emit(it) }
            } else {
                emit(Result.Error(e))
            }
        }
    }

    override fun getCryptoCurrencyById(id: String): Flow<Result<CryptoCurrency>> = flow {
        emit(Result.Loading)

        try {
            dao.getCryptoCurrencyById(id).collect { entity ->
                if (entity != null) {
                    emit(Result.Success(entity.toDomain()))
                } else {
                    emit(Result.Error(Exception("Cryptocurrency not found")))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun refreshCryptoCurrencies() {
        try {
            val response = api.getCryptoMarkets()
            val entities = response.map { it.toEntity() }
            dao.deleteAll()
            dao.insertAll(entities)
        } catch (e: Exception) {
            // Handle error - could log or rethrow
            throw e
        }
    }
}