package com.example.pertemuan_3.data

import kotlinx.coroutines.flow.Flow

expect class NetworkMonitor {
    fun observeConnectivity(): Flow<Boolean>
}