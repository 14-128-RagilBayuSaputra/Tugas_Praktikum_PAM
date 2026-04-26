package com.example.pertemuan_3.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class NetworkMonitor {
    actual fun observeConnectivity(): Flow<Boolean> = flowOf(true)
}