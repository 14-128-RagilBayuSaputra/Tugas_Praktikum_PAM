package com.example.pertemuan_3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Notes : BottomNavItem(Screen.Notes.route, Icons.Default.Home, "Notes")
    object Favorites : BottomNavItem(Screen.Favorites.route, Icons.Default.Favorite, "Favorites")
    object Profile : BottomNavItem(Screen.Profile.route, Icons.Default.Person, "Profile")
}