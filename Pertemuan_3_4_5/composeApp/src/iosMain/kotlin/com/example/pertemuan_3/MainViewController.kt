package com.example.pertemuan_3

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import com.example.pertemuan_3.di.appModules

private var isKoinInitialized = false

fun MainViewController() = ComposeUIViewController {
    if (!isKoinInitialized) {
        startKoin {
            modules(appModules)
        }
        isKoinInitialized = true
    }

    App()
}