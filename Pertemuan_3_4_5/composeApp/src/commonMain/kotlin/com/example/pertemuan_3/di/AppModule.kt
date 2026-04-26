package com.example.pertemuan_3.di

import com.example.notes.db.NotesDatabase
import com.example.pertemuan_3.data.NoteRepository
import com.example.pertemuan_3.db.DatabaseDriverFactory
import com.example.pertemuan_3.data.DeviceInfo
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val commonModule = module {
    single { NotesDatabase(driver = get<DatabaseDriverFactory>().createDriver()) }
    single { NoteRepository(database = get()) }

    single { DeviceInfo() }
}

val appModules = listOf(commonModule, platformModule)