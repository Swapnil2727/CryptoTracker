package com.app.cryptotracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.enro.core.controller.NavigationApplication
import dev.enro.core.controller.createNavigationController

@HiltAndroidApp
class CryptoTrackerApp() : Application(), NavigationApplication {
    override val navigationController = createNavigationController {}
}
