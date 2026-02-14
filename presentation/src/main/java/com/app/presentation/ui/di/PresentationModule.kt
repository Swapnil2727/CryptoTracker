package com.app.presentation.ui.di

import com.app.presentation.navigation.Destination
import com.app.presentation.navigation.Navigator
import com.app.presentation.ui.cryptodetail.CryptoDetailScreen
import com.app.presentation.ui.cryptolist.CryptoListScreen
import com.app.presentation.viewmodel.cryptodetail.CryptoDetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val presentationModule = module {
    navigation<Destination.CryptoListDestination> {
        CryptoListScreen(
            koinViewModel(),
            onCryptoClick = { id ->
                get<Navigator>().goTo(Destination.CryptoDetailDestination(id))
            }
        )
    }
    navigation<Destination.CryptoDetailDestination> { destination ->
        val cryptoDetailViewModel: CryptoDetailViewModel =
            koinViewModel { parametersOf(destination.id) }
        CryptoDetailScreen(
            viewModel = cryptoDetailViewModel,
            onBackClick = {
                get<Navigator>().goBack()
            }
        )
    }
}