package com.example.pertemuan_3.data

actual class DeviceInfo {
    actual fun getDeviceName(): String {
        return System.getProperty("os.name") ?: "Desktop"
    }

    actual fun getOsVersion(): String {
        return System.getProperty("os.version") ?: "Unknown Version"
    }
}