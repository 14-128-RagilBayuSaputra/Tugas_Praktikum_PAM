package com.example.newsfeedsimulator

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class News(val id: Int, val title: String, val category: String)

class NewsFeedSimulator {
    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    fun getNewsStream(): Flow<News> = flow {
        var id = 1
        val categories = listOf("Tech", "Sports", "Politics")

        while (true) {
            delay(2000)
            val category = categories.random()
            emit(News(id, "Berita $category Hari Ini #$id", category))
            id++
        }
    }.catch { e ->
        println("Terjadi error pada stream: ${e.message}")
    }

    suspend fun fetchNewsDetail(newsId: Int): String {
        delay(1000) // Simulasi network delay 1 detik
        return "Memuat detail lengkap untuk berita ID $newsId..."
    }

    fun markAsRead() {
        _readCount.value++
    }
}

fun main() = runBlocking {
    val simulator = NewsFeedSimulator()
    println("=== Memulai News Feed Simulator ===\n")

    val job = launch {
        simulator.getNewsStream()
            .filter { it.category == "Tech" }
            .map { "[KATEGORI ${it.category.uppercase()}] - ${it.title}" }
            .collect { formattedNews ->
                println("Berita Baru Masuk: $formattedNews")

                val detail = simulator.fetchNewsDetail(1)
                println("Detail: $detail")

                simulator.markAsRead()
                println("-> Total berita Tech yang dibaca: ${simulator.readCount.value}\n")
            }
    }

    delay(15000)
    job.cancel()
    println("=== Simulasi Selesai ===")
}