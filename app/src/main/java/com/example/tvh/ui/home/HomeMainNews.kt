package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.unit.dp
import com.example.tvh.entity.Article
import com.example.tvh.services.ImageLoader
import com.example.tvh.services.Navigator
import com.example.tvh.ui.common.ArticleContent

@Composable
fun HomeMainNews(
    article: Article,
    imageLoader: ImageLoader,
    onNavigateToArticle: (Navigator.Screen) -> Unit,
    onNavigateToSite: (String) -> Unit
) {
    VerticalScroller {
        Column(modifier = Modifier.padding(10.dp)) {
            ArticleContent(
                article = article,
                imageLoader = imageLoader,
                onNavigateToArticle = onNavigateToArticle,
                onNavigateToSite = onNavigateToSite
            )
        }
    }
}
