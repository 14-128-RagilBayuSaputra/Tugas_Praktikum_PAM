package com.example.pertemuan_3.navigation

sealed class Screen(val route: String) {
    object Notes : Screen("notes")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")

    object AddNote : Screen("add_note")

    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
}