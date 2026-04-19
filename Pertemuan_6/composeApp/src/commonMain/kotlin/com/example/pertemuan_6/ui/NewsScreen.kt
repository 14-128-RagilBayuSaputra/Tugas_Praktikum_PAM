package com.example.pertemuan_6.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pertemuan_6.model.Article
import com.example.pertemuan_6.viewmodel.NewsUiState
import com.example.pertemuan_6.viewmodel.NewsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun NewsScreen(viewModel: NewsViewModel, onNavigateToDetail: (Article) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is NewsUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NewsUiState.Success -> {
            NewsList(
                articles = state.data.articles,
                onRefresh = { viewModel.fetchNews() },
                onArticleClick = onNavigateToDetail
            )
        }
        is NewsUiState.Error -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Error: ${state.message}", color = Color.Red, modifier = Modifier.padding(16.dp))
                Button(onClick = { viewModel.fetchNews() }) {
                    Text("Retry")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsList(articles: List<Article>, onRefresh: () -> Unit, onArticleClick: (Article) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Berita Terkini", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                actions = {
                    Button(onClick = onRefresh, modifier = Modifier.padding(end = 8.dp)) {
                        Text("Refresh")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            items(articles) { article ->
                NewsItemCard(
                    article = article,
                    onClick = { onArticleClick(article) }
                )
            }
        }
    }
}

@Composable
fun NewsItemCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            val imageUrl = article.urlToImage
            if (imageUrl != null && imageUrl.isNotEmpty()) {
                KamelImage(
                    resource = asyncPainterResource(data = imageUrl),
                    contentDescription = "Gambar Berita",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    onLoading = { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) },
                    onFailure = { Text("Gagal memuat gambar", color = Color.Gray) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                text = article.title ?: "Tanpa Judul",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.description ?: "Tidak ada deskripsi tersedia.",
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}