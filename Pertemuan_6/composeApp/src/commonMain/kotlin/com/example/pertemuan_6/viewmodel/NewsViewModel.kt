package com.example.pertemuan_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan_6.model.NewsResponse
import com.example.pertemuan_6.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val data: NewsResponse) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading

            val result = repository.getTopNews()

            result.onSuccess { response ->
                _uiState.value = NewsUiState.Success(response)
            }
            result.onFailure { error ->
                _uiState.value = NewsUiState.Error(error.message ?: "Terjadi kesalahan jaringan.")
            }
        }
    }
}