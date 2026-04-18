package com.example.pertemuan_3

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Pertemuan_3",
    ) {
        App()
    }
}