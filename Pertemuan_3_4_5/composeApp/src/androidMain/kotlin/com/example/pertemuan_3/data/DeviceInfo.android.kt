package com.example.pertemuan_3.data

import android.os.Build

actual class DeviceInfo {
    actual fun getDeviceName(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }

    actual fun getOsVersion(): String {
        return "Android ${Build.VERSION.RELEASE}"
    }
}