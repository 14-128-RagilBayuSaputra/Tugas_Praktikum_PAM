package com.example.pertemuan_6.repository

import com.example.pertemuan_6.model.NewsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NewsRepository {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun getTopNews(): Result<NewsResponse> {
        return try {
            val apiKey = "100040e4e1cb4f85841eb3f08d3891ec"
            val url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=$apiKey"

            val response: NewsResponse = client.get(url).body()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}