package com.example.pertemuan_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// --- IMPORT BARU PABRIK SOPIR ---
import com.example.pertemuan_3.db.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            // Memberikan "ID Card" Android (this) agar bisa membuat file database di memori HP
            App(driverFactory = DatabaseDriverFactory(context = this))
        }
    }
}

/*
@Preview
@Composable
fun AppAndroidPreview() {
    // Preview dimatikan sementara karena Preview tidak bisa membaca Database fisik
    // App()
}
*/