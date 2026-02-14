package com.app.data.di

import androidx.room.Room
import com.app.data.database.CryptoDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CryptoDatabase::class.java,
            CryptoDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<CryptoDatabase>().cryptoDao() }
}