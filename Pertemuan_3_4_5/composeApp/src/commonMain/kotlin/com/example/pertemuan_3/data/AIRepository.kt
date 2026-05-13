package com.example.pertemuan_3.data

interface AIRepository {
    suspend fun chat(message: String): Result<String>
    fun clearChatHistory()
}

class AIRepositoryImpl(
    private val geminiService: GeminiService
) : AIRepository {

    override suspend fun chat(message: String): Result<String> {
        return geminiService.chat(message)
    }

    override fun clearChatHistory() {
        geminiService.clearHistory()
    }
}