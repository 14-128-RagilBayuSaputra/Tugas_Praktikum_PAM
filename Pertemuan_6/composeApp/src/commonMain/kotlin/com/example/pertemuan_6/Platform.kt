package com.example.pertemuan_6

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform