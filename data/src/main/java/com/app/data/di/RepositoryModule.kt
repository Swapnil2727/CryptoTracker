package com.app.data.di

import com.app.data.repo.CryptoRepositoryImpl
import com.app.domain.repository.CryptoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CryptoRepository> {
        CryptoRepositoryImpl(
            get(),
            dao = get()
        )
    }
}