package com.user.tvh.ui.home

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.unit.dp
import com.user.tvh.entity.Article
import com.user.tvh.services.ImageLoader
import com.user.tvh.services.Navigator

@Composable
fun Home(
    articles: List<Article>,
    imageLoader: ImageLoader,
    onNavigateToArticle: (screen: Navigator.Screen) -> Unit,
    onNavigateToSite: (String) -> Unit
) {
    val rowModifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    val newsArticle = articles.find { it.asNews == 1 }
    val otherArticles = articles.filter { it.asNews == 0 }
    Column(
        modifier = Modifier.padding(bottom = 50.dp)
    ) {
        if (articles.isEmpty()) {
            Text("Пусто", modifier = Modifier.padding(8.dp))
            return@Column
        }
        if (newsArticle != null) {
            Box(modifier = Modifier.weight(1f)) {
                HomeMainNews(
                    article = newsArticle,
                    imageLoader = imageLoader,
                    onNavigateToArticle = onNavigateToArticle,
                    onNavigateToSite = onNavigateToSite
                )
            }
        }
        if (otherArticles.isNotEmpty()) {
            Box(modifier = Modifier.weight(1f)) {
                HomeArticles(
                    articles = otherArticles,
                    rowModifier = rowModifier,
                    onNavigateToArticle = onNavigateToArticle
                )
            }
        }
    }
}
