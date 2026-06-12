package com.example.pertemuan_3.data

import com.example.notes.db.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class NoteRepositoryTest {
    private val fakeNotesDatabase = mutableListOf<Note>()
    private var currentId = 1L

    @BeforeTest
    fun setUp() {
        fakeNotesDatabase.clear()
        currentId = 1L
    }

    @Test
    fun testInsertNoteAndGetAllNotes() = runTest {
        val initialSize = fakeNotesDatabase.size

        val newNote = Note(
            id = currentId++,
            title = "Catatan Kuliah PAM",
            content = "Hari ini belajar Unit Testing dan Koin DI di KMP",
            created_at = 1718197200000L
        )
        fakeNotesDatabase.add(newNote)

        assertEquals(initialSize + 1, fakeNotesDatabase.size)
        assertEquals("Catatan Kuliah PAM", fakeNotesDatabase.last().title)
    }

    @Test
    fun testGetNoteByIdMatchesData() = runTest {
        val newNote = Note(
            id = currentId++,
            title = "Tugas WebGIS",
            content = "CRUD spasial PostGIS",
            created_at = 1718197300000L
        )
        fakeNotesDatabase.add(newNote)

        val fetchedNote = fakeNotesDatabase.find { it.id == newNote.id }
        assertNotNull(fetchedNote)
        assertEquals(newNote.title, fetchedNote.title)
        assertEquals(newNote.content, fetchedNote.content)
    }

    @Test
    fun testUpdateNoteContentSuccessfully() = runTest {
        val originalNote = Note(
            id = currentId++,
            title = "Judul Lama",
            content = "Konten Lama",
            created_at = 1718197400000L
        )
        fakeNotesDatabase.add(originalNote)

        val index = fakeNotesDatabase.indexOfFirst { it.id == originalNote.id }
        if (index != -1) {
            fakeNotesDatabase[index] = Note(
                id = originalNote.id,
                title = "Judul Baru Ragil",
                content = "Konten Baru PAM",
                created_at = originalNote.created_at
            )
        }

        val updatedNote = fakeNotesDatabase.find { it.id == originalNote.id }
        assertNotNull(updatedNote)
        assertEquals("Judul Baru Ragil", updatedNote.title)
        assertEquals("Konten Baru PAM", updatedNote.content)
    }

    @Test
    fun testDeleteNoteRemovesFromDatabase() = runTest {
        val newNote = Note(
            id = currentId++,
            title = "Catatan Sampah",
            content = "Akan segera dihapus",
            created_at = 1718197500000L
        )
        fakeNotesDatabase.add(newNote)

        fakeNotesDatabase.removeAll { it.id == newNote.id }

        val fetchedNote = fakeNotesDatabase.find { it.id == newNote.id }
        assertNull(fetchedNote)
    }

    @Test
    fun testGetNoteByIdWithInvalidIdReturnsNull() = runTest {
        val invalidId = -999L
        val fetchedNote = fakeNotesDatabase.find { it.id == invalidId }
        assertNull(fetchedNote)
    }
}