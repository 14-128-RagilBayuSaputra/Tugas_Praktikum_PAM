package com.example.pertemuan_3.screens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ProfileHeader(name: String, bio: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto Profil",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = bio,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
@Composable
fun InfoItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp)
    }
}
@Composable
fun ProfileCard(email: String, phone: String, location: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Informasi Kontak",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp)
            )
            InfoItem(icon = Icons.Default.Email, text = email)
            InfoItem(icon = Icons.Default.Phone, text = phone)
            InfoItem(icon = Icons.Default.LocationOn, text = location)
        }
    }
}
@Composable
fun LabeledTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    )
}

@Composable
fun ProfileScreen(viewModel: com.example.pertemuan_3.viewmodel.ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var showDetails by remember { mutableStateOf(false) }

    MaterialTheme(colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                    Text("Dark Mode", modifier = Modifier.padding(end = 8.dp))
                    Switch(checked = uiState.isDarkMode, onCheckedChange = { viewModel.toggleDarkMode(it) })
                }

                Spacer(modifier = Modifier.height(16.dp))
                if (uiState.isEditing) {
                    // TAMPILAN MODE EDIT
                    Text("Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                    LabeledTextField(label = "Nama", value = uiState.name, onValueChange = { viewModel.updateName(it) })
                    LabeledTextField(label = "Bio", value = uiState.bio, onValueChange = { viewModel.updateBio(it) })
                    LabeledTextField(label = "Email", value = uiState.email, onValueChange = { viewModel.updateEmail(it) })
                    LabeledTextField(label = "Telepon", value = uiState.phone, onValueChange = { viewModel.updatePhone(it) })
                    LabeledTextField(label = "Lokasi", value = uiState.location, onValueChange = { viewModel.updateLocation(it) })

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.toggleEditMode() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Simpan Profil")
                    }
                } else {
                    ProfileHeader(name = uiState.name, bio = uiState.bio)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { viewModel.toggleEditMode() }) {
                        Text("Edit Profil")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = { showDetails = !showDetails }) {
                        Text(if (showDetails) "Sembunyikan Info Kontak" else "Tampilkan Info Kontak")
                    }

                    AnimatedVisibility(visible = showDetails) {
                        ProfileCard(email = uiState.email, phone = uiState.phone, location = uiState.location)
                    }
                }
            }
        }
    }
}