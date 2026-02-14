package com.app.cryptotracker.di

import com.app.cryptotracker.BuildConfig
import com.app.presentation.navigation.Destination
import com.app.presentation.navigation.Navigator
import com.app.presentation.ui.di.presentationModule
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<String>(named("ApiKey")) { BuildConfig.COINGECKO_API_KEY }
    includes(presentationModule)

    single { Navigator(startDestination = Destination.CryptoListDestination) }

}