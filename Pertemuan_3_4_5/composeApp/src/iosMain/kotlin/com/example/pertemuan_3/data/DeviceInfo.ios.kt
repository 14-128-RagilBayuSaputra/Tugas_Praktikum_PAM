package com.example.pertemuan_3.data

import platform.UIKit.UIDevice

actual class DeviceInfo {
    actual fun getDeviceName(): String {
        return UIDevice.currentDevice.name
    }

    actual fun getOsVersion(): String {
        return "iOS ${UIDevice.currentDevice.systemVersion}"
    }
}