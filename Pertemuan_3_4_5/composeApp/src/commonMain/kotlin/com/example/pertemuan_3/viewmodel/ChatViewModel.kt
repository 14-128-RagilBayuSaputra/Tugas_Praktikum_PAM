package com.example.pertemuan_3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan_3.data.AIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ChatViewModel(
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun sendMessage(message: String) {
        if (message.isBlank()) return

        _uiState.update { it.copy(
            messages = it.messages + ChatMessage(text = message, isUser = true),
            isLoading = true,
            error = null
        )}

        viewModelScope.launch {
            aiRepository.chat(message)
                .onSuccess { response ->
                    // Memaksa pembaruan state kembali ke Main UI Thread
                    withContext(Dispatchers.Main) {
                        _uiState.update { state ->
                            state.copy(
                                messages = state.messages + ChatMessage(text = response, isUser = false),
                                isLoading = false
                            )
                        }
                    }
                }
                .onFailure { error ->
                    // Memaksa pembaruan error kembali ke Main UI Thread
                    withContext(Dispatchers.Main) {
                        _uiState.update { state ->
                            state.copy(
                                error = error.message ?: "Terjadi kesalahan pada jaringan",
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    fun clearHistory() {
        aiRepository.clearChatHistory()
        _uiState.update { ChatUiState() }
    }
}