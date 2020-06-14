package com.user.tvh.ui.home

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.unit.dp
import com.user.tvh.entity.Article
import com.user.tvh.services.ImageLoader
import com.user.tvh.services.Navigator
import com.user.tvh.ui.common.ArticleContent

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
