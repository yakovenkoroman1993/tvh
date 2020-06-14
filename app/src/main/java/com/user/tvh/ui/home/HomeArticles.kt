package com.user.tvh.ui.home

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.ripple
import androidx.ui.unit.dp
import com.user.tvh.entity.Article
import com.user.tvh.services.Navigator
import com.user.tvh.utils.Utils

@Composable
fun HomeArticles(
    articles: List<Article>,
    rowModifier: Modifier,
    onNavigateToArticle: (screen: Navigator.Screen) -> Unit
) {
    VerticalScroller {
        Column {
            articles.forEach { article ->
                ArticleItem(
                    modifier = rowModifier,
                    article = article,
                    onClick = {
                        onNavigateToArticle(
                            Navigator.Screen.ArticleScreen(article)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit
) {
    Row(modifier = modifier) {
        Clickable(
            modifier = Modifier.ripple(),
            onClick = onClick
        ) {
            Column(modifier = Modifier.padding(5.dp).weight(1f)) {
                ProvideEmphasis(EmphasisAmbient.current.high) {
                    Text(article.name, style = MaterialTheme.typography.subtitle1)
                }
                ProvideEmphasis(EmphasisAmbient.current.medium) {
                    Text(
                        text = " - ${Utils.DateTime.getPrettyTime(article.createdAt)}",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}
