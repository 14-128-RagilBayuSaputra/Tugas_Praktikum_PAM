package com.example.pertemuan_3.di

import com.example.pertemuan_3.db.DatabaseDriverFactory
import com.example.pertemuan_3.data.NetworkMonitor
import org.koin.dsl.module

actual val platformModule = module {
    single<DatabaseDriverFactory> { DatabaseDriverFactory() }
    single { NetworkMonitor() }
}