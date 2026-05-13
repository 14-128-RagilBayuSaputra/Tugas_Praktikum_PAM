package com.example.pertemuan_3.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GeminiService(private val client: HttpClient) {
    private val baseUrl = "https://generativelanguage.googleapis.com/v1"

    private val model = "gemini-1.5-flash"

    private val conversationHistory = mutableListOf<Content>()

    suspend fun chat(userMessage: String): Result<String> = runCatching {

        if (ApiConfig.geminiApiKey.isEmpty()) {
            throw Exception("API Key kosong! Pastikan local.properties sudah benar dan lakukan Rebuild.")
        }

        conversationHistory.add(
            Content(parts = listOf(Part(text = userMessage)), role = "user")
        )

        val request = GeminiRequest(contents = conversationHistory.toList())

        val httpResponse: HttpResponse = client.post("$baseUrl/models/$model:generateContent") {
            contentType(ContentType.Application.Json)
            parameter("key", ApiConfig.geminiApiKey)
            setBody(request)
        }

        val rawResponse = httpResponse.bodyAsText()

        if (httpResponse.status.value !in 200..299) {
            throw Exception("Ditolak Google (${httpResponse.status.value}): $rawResponse")
        }

        val response: GeminiResponse = httpResponse.body()
        val assistantMessage = response.candidates.first().content

        conversationHistory.add(assistantMessage)

        assistantMessage.parts.first().text
    }

    fun clearHistory() {
        conversationHistory.clear()
    }
}