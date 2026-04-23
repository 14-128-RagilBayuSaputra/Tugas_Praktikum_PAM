package com.example.pertemuan_3.db

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory{
    fun createDriver(): SqlDriver
}