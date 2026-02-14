package com.app.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {

    @Serializable
    data object CryptoListDestination

    @Serializable
    data class CryptoDetailDestination(val id: String)
}