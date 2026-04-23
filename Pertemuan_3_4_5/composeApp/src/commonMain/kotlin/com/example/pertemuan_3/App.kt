package com.example.pertemuan_3

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pertemuan_3.screens.EditNoteScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan_3.components.BottomNavigationBar
import com.example.pertemuan_3.navigation.Screen
import com.example.pertemuan_3.screens.AddNoteScreen
import com.example.pertemuan_3.screens.FavoritesScreen
import com.example.pertemuan_3.screens.NotesScreen
import com.example.pertemuan_3.screens.ProfileScreen

// --- IMPORT BARU UNTUK DATABASE ---
import com.example.pertemuan_3.db.DatabaseDriverFactory
import com.example.notes.db.NotesDatabase
import com.example.pertemuan_3.data.NoteRepository

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    val navController = rememberNavController()

    val database = NotesDatabase(driverFactory.createDriver())
    val repository = NoteRepository(database)

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = { BottomNavigationBar(navController = navController) }
            ) { paddingValues ->

                NavHost(
                    navController = navController,
                    startDestination = Screen.Notes.route,
                    modifier = Modifier.padding(paddingValues).fillMaxSize()
                ) {
                    // --- 3. OPER KASIR KE HALAMAN YANG MEMBUTUHKAN CATATAN ---
                    composable(Screen.Notes.route) {
                        NotesScreen(navController = navController, repository = repository)
                    }
                    composable(Screen.Favorites.route) { FavoritesScreen() }
                    composable(Screen.Profile.route) { ProfileScreen() }

                    composable(Screen.AddNote.route) {
                        AddNoteScreen(navController = navController, repository = repository)
                    }
                    composable(
                        route = "edit_note/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getLong("id") ?: 0L
                        EditNoteScreen(
                            navController = navController,
                            repository = repository,
                            noteId = id
                        )
                    }
                }
            }
        }
    }
}