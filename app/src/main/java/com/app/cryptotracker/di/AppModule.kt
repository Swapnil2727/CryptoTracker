package com.app.cryptotracker.di

import com.app.cryptotracker.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module


val appModule = module {

    single(named("ApiKey")) { BuildConfig.COINGECKO_API_KEY }

}