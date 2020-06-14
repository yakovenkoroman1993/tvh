package com.user.tvh.ui.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButton
import androidx.ui.unit.dp
import com.user.tvh.entity.Article
import com.user.tvh.services.ImageLoader
import com.user.tvh.services.Navigator

@Composable
fun ArticleContent(
    article: Article,
    imageLoader: ImageLoader,
    visibleDescription: Boolean = false,
    onNavigateToArticle: (Navigator.Screen) -> Unit,
    onNavigateToSite: (String) -> Unit
) {
    Text(
        text = article.name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )

    if (article.imgUrl.isNotEmpty() && !visibleDescription) {
        ImageBox(article = article, imageLoader = imageLoader)
    }
    if (visibleDescription) {
        if (article.srcUrl.isNotEmpty()) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp),
                onClick = { onNavigateToSite(article.srcUrl) }
            ) {
                Text("Перейти к источнику")
            }
        }
        VerticalScroller {
            Column {
                if (article.imgUrl.isNotEmpty()) {
                    ImageBox(article = article, imageLoader = imageLoader)
                }
                Text(
                    text = article.desc,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    } else {
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            onClick = { onNavigateToArticle(Navigator.Screen.ArticleScreen(article)) }
        ) {
            Text("Подробнее")
        }
    }
}

@Composable
fun ImageBox(article: Article, imageLoader: ImageLoader) {
    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        imageLoader.UrlImage(url = article.imgUrl) {
            Image(it,
                modifier = Modifier
                    .preferredHeightIn(maxHeight = 200.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp))
            )
        }
    }
}