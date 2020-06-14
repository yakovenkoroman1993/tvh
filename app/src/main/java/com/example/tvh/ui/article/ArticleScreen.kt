package com.example.tvh.ui.article

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import com.example.tvh.commander.ArticleCommander
import com.example.tvh.di.IAppContainer
import com.example.tvh.entity.Article
import com.example.tvh.services.ComponentsWithClipboardManager
import com.example.tvh.services.ImageLoader
import com.example.tvh.services.Navigator
import com.example.tvh.ui.common.ArticleContent
import com.example.tvh.ui.common.ArticleForm

@Composable
fun ArticleScreen(
    article: Article,
    appContainer: IAppContainer
) {
    val navigator = appContainer.navigator
    val imageLoader = appContainer.imageLoader
    val componentsWithClipboardManager = appContainer.componentsWithClipboardManager
    val articleCommander = appContainer.articleCommander

    ArticleDetails(
        article = article,
        style = MaterialTheme.typography.subtitle1,
        imageLoader = imageLoader,
        componentsWithClipboardManager = componentsWithClipboardManager,
        articleCommander = articleCommander,
        onNavigateToSite = {
            navigator.navigateToSite(it)
        },
        onNavigateTo = {
            navigator.navigateTo(it)
        }
    )
}

private enum class Tabs(val title: String) {
    View("Просмотр"),
    Edit("Редактирование")
}

@Composable
fun ArticleDetails(
    article: Article,
    style: TextStyle,
    imageLoader: ImageLoader,
    componentsWithClipboardManager: ComponentsWithClipboardManager,
    articleCommander: ArticleCommander,
    onNavigateToSite: (String) -> Unit,
    onNavigateTo: (Navigator.Screen) -> Unit = {}
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
                IconButton(onClick = { onNavigateTo(Navigator.Screen.HomeScreen) }) {
                    Icon(Icons.Filled.ArrowBack)
                }
            }
        )

        val (tab, setTab) = state { Tabs.View }
        Column {
            TabRow(
                items = Tabs.values().map { it.title },
                selectedIndex = tab.ordinal
            ) { index, text ->
                Tab(
                    text = { Text(text) },
                    selected = tab.ordinal == index,
                    onSelected = {
                        setTab(Tabs.values()[index])
                    }
                )
            }
            when (tab) {
                Tabs.View -> ViewTab(
                    article = article,
                    imageLoader = imageLoader,
                    onNavigateToArticle = onNavigateTo,
                    onNavigateToSite = onNavigateToSite
                )
                Tabs.Edit -> EditTab(
                    article = article,
                    componentsWithClipboardManager = componentsWithClipboardManager,
                    onUpdateArticle = { article ->
                        articleCommander.update(article)
                        onNavigateTo(Navigator.Screen.ArticleScreen(article))
                    }
                )
            }
        }
    }
}

@Composable
fun ViewTab(
    article: Article,
    imageLoader: ImageLoader,
    onNavigateToArticle: (Navigator.Screen) -> Unit,
    onNavigateToSite: (String) -> Unit
) {
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

@Composable
fun EditTab(
    article: Article,
    componentsWithClipboardManager: ComponentsWithClipboardManager,
    onUpdateArticle: (article: Article) -> Unit
) {
    val nameTextFieldState = state { TextFieldValue(article.name) }
    val imgUrlTextFieldState = state { TextFieldValue(article.imgUrl) }
    val srcUrlTextFieldState = state { TextFieldValue(article.srcUrl) }
    val descTextFieldState = state { TextFieldValue(article.desc) }
    VerticalScroller {
        Column(modifier = Modifier.padding(10.dp)) {
            ArticleForm(
                nameTextFieldState = nameTextFieldState,
                imgUrlTextFieldState = imgUrlTextFieldState,
                srcUrlTextFieldState = srcUrlTextFieldState,
                descTextFieldState = descTextFieldState,
                componentsWithClipboardManager = componentsWithClipboardManager
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onUpdateArticle(
                            article.copy(
                                name = nameTextFieldState.value.text,
                                imgUrl = imgUrlTextFieldState.value.text,
                                desc = descTextFieldState.value.text
                            )
                        )
                    }
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}