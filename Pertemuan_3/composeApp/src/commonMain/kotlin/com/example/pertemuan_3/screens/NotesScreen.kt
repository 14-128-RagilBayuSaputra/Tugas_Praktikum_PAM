package com.example.pertemuan_3.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pertemuan_3.navigation.Screen

@Composable
fun NotesScreen(navController: NavController) { // Tambahkan NavController di sini
    Scaffold(
        floatingActionButton = {
            // Tombol Melayang (FAB)
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddNote.route) // Perintah pindah ke layar AddNote
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Catatan")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            Text("Daftar Catatan Kosong", fontSize = 20.sp)
        }
    }
}