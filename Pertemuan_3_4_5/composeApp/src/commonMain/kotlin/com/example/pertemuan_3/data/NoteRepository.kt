package com.example.pertemuan_3.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.notes.db.Note
import com.example.notes.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NoteRepository(database: NotesDatabase){

    private val queries = database.noteQueries

    open fun getAllNotes(): Flow<List<Note>> {
        return queries.selectall().asFlow().mapToList(Dispatchers.Default)
    }

    open fun insertNote(title: String, content: String, createdAt: Long) {
        queries.insert(title = title, content = content, created_at = createdAt)
    }

    open fun updateNote(id: Long, title: String, content: String) {
        queries.update(title = title, content = content, id = id)
    }

    open fun deleteNote(id: Long) {
        queries.delete(id = id)
    }

    open fun getNoteById(id: Long): Flow<Note?> {
        return queries.selectById(id).asFlow().mapToOneOrNull(Dispatchers.Default)
    }
}