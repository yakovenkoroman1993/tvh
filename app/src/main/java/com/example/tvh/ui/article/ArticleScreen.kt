package com.example.tvh.ui.article

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import com.example.tvh.di.IAppContainer
import com.example.tvh.entity.Article
import com.example.tvh.services.ImageLoader
import com.example.tvh.services.Navigator
import com.example.tvh.ui.common.ArticleContent

@Composable
fun ArticleScreen(
    article: Article,
    appContainer: IAppContainer
) {
    val navigator = appContainer.navigator
    val imageLoader = appContainer.imageLoader

    ArticleDetails(
        article = article,
        style = MaterialTheme.typography.subtitle1,
        imageLoader = imageLoader,
        onNavigateToSite = {
            navigator.navigateToSite(it)
        },
        onNavigateToArticle = {
            navigator.navigateTo(it)
        }
    )
}

@Composable
fun ArticleDetails(
    article: Article,
    style: TextStyle,
    imageLoader: ImageLoader,
    onNavigateToSite: (String) -> Unit,
    onNavigateToArticle: (Navigator.Screen) -> Unit = {}
) {
    Column(modifier = Modifier.padding(bottom = 50.dp)) {
        TopAppBar(
            title = {
                Text(
                    text = "Подробности",
                    modifier = Modifier.padding(12.dp),
                    style = style
                )
            },
            navigationIcon = {
                IconButton(onClick = { onNavigateToArticle(Navigator.Screen.HomeScreen) }) {
                    Icon(Icons.Filled.ArrowBack)
                }
            }
        )

        Column(modifier = Modifier.padding(10.dp)) {
            ArticleContent(
                article = article,
                imageLoader = imageLoader,
                visibleDescription = true,
                onNavigateToArticle = onNavigateToArticle,
                onNavigateToSite = onNavigateToSite
            )
        }
    }
}
