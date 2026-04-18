package com.example.pertemuan_3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pertemuan_3.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun updateName(newName: String) { _uiState.update { it.copy(name = newName) } }
    fun updateBio(newBio: String) { _uiState.update { it.copy(bio = newBio) } }
    fun updateEmail(newEmail: String) { _uiState.update { it.copy(email = newEmail) } }
    fun updatePhone(newPhone: String) { _uiState.update { it.copy(phone = newPhone) } }
    fun updateLocation(newLocation: String) { _uiState.update { it.copy(location = newLocation) } }

    fun toggleDarkMode(isDark: Boolean) { _uiState.update { it.copy(isDarkMode = isDark) } }
    fun toggleEditMode() { _uiState.update { it.copy(isEditing = !it.isEditing) } }
}