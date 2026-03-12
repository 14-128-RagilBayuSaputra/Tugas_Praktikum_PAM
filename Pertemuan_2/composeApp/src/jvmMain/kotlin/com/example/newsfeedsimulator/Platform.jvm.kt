package com.example.newsfeedsimulator

class JVMPlatform: Platform {
    override val name: String = "Desktop JVM"
}

actual fun getPlatform(): Platform = JVMPlatform()