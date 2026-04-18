package com.example.pertemuan_3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform