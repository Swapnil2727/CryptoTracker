package com.app.data.di

import android.content.Context
import androidx.room.Room
import com.app.data.database.CryptoDatabase
import com.app.data.local.dao.CryptoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCryptoDatabase(
        @ApplicationContext context: Context
    ): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            CryptoDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoDao(database: CryptoDatabase): CryptoDao {
        return database.cryptoDao()
    }
}