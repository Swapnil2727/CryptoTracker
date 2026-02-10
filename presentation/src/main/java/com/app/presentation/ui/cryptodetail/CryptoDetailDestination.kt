package com.app.presentation.ui.cryptodetail

import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoDetailDestination(val cryptoId: String) : NavigationKey.SupportsPush