package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.ripple
import androidx.ui.unit.dp
import com.example.tvh.entity.Article
import com.example.tvh.services.Navigator
import com.example.tvh.utils.Utils

@Composable
fun HomeArticles(
    articles: List<Article>,
    rowModifier: Modifier,
    onRemoveArticle: (article: Article) -> Unit,
    onNavigateToArticle: (screen: Navigator.Screen) -> Unit
) {
    VerticalScroller {
        Column {
            articles.forEach { article ->
                ArticleItem(
                    modifier = rowModifier,
                    article = article,
                    onRemove = {
                        onRemoveArticle(article)
                    },
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
    onRemove: () -> Unit,
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
        Row(
            modifier = Modifier.padding(top = 5.dp)
        ) {
            TextButton(onClick = onRemove) {
                Text("Удалить")
            }
        }
    }
}
