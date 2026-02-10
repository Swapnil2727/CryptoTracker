package com.app.cryptotracker

import android.app.Application
import com.app.data.di.databaseModule
import com.app.data.di.repositoryModule
import com.app.presentation.viewmodel.di.viewModelModule
import dev.enro.annotations.NavigationComponent
import dev.enro.core.controller.NavigationApplication
import dev.enro.core.controller.createNavigationController
import networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.KoinApplicationDslMarker


@NavigationComponent
class CryptoTrackerApp() : Application(), NavigationApplication {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoTrackerApp)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule,
            )
        }
    }

    override val navigationController = createNavigationController {}
}
