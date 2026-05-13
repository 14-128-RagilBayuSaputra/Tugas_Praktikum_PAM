package com.example.pertemuan_3.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import com.example.notes.db.NotesDatabase
import com.example.pertemuan_3.data.NoteRepository
import com.example.pertemuan_3.db.DatabaseDriverFactory
import com.example.pertemuan_3.data.DeviceInfo
import com.example.pertemuan_3.data.GeminiService
import com.example.pertemuan_3.data.AIRepository
import com.example.pertemuan_3.data.AIRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.pertemuan_3.viewmodel.ChatViewModel

expect val platformModule: Module

val commonModule = module {
    single { NotesDatabase(driver = get<DatabaseDriverFactory>().createDriver()) }
    single { NoteRepository(database = get()) }
    single { DeviceInfo() }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    single { GeminiService(get()) }

    single<AIRepository> { AIRepositoryImpl(get()) }
    factory { ChatViewModel(get()) }
}

val appModules = listOf(commonModule, platformModule)