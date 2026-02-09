package com.app.cryptotracker.di

import com.app.cryptotracker.BuildConfig
import com.app.data.di.ApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String {
        return BuildConfig.COINGECKO_API_KEY
    }
}