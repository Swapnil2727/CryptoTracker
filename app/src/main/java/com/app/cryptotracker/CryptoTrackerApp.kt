package com.app.cryptotracker

import android.app.Application
import com.app.cryptotracker.di.appModule
import com.app.data.di.databaseModule
import com.app.data.di.networkModule
import com.app.data.di.repositoryModule
import com.app.presentation.viewmodel.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CryptoTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoTrackerApp)
            modules(
                appModule,
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule,
            )
        }
    }
}
