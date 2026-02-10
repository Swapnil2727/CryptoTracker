package com.app.presentation.viewmodel.di

import com.app.presentation.viewmodel.cryptodetail.CryptoDetailViewModel
import com.app.presentation.viewmodel.cryptolist.CryptoListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CryptoListViewModel)
    viewModelOf(::CryptoDetailViewModel)
}