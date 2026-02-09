package com.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.data.local.CryptoCurrencyEntity
import com.app.data.local.dao.CryptoDao

@Database(
    entities = [CryptoCurrencyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao

    companion object {
        const val DATABASE_NAME = "crypto_tracker_db"
    }
}