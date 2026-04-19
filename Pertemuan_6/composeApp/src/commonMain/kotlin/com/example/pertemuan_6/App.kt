package com.example.pertemuan_6

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan_6.model.Article
import com.example.pertemuan_6.ui.NewsDetailScreen
import com.example.pertemuan_6.ui.NewsScreen
import com.example.pertemuan_6.viewmodel.NewsViewModel

@Composable
fun App() {
    MaterialTheme {
        val newsViewModel = viewModel { NewsViewModel() }
        var selectedArticle by remember { mutableStateOf<Article?>(null) }

        if (selectedArticle == null) {
            NewsScreen(
                viewModel = newsViewModel,
                onNavigateToDetail = { articleYangDiklik ->
                    selectedArticle = articleYangDiklik
                }
            )
        } else {
            NewsDetailScreen(
                article = selectedArticle!!,
                onBackClick = {
                    selectedArticle = null
                }
            )
        }
    }
}