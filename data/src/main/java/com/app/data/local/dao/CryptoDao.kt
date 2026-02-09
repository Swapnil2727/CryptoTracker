package com.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.data.local.CryptoCurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {

    @Query("SELECT * FROM cryptocurrencies ORDER BY marketCapRank ASC")
    fun getAllCryptoCurrencies(): Flow<List<CryptoCurrencyEntity>>

    @Query("SELECT * FROM cryptocurrencies WHERE id = :id")
    fun getCryptoCurrencyById(id: String): Flow<CryptoCurrencyEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptocurrencies: List<CryptoCurrencyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cryptocurrency: CryptoCurrencyEntity)

    @Query("DELETE FROM cryptocurrencies")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM cryptocurrencies")
    suspend fun getCount(): Int
}