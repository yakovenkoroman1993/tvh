package com.example.tvh.ui

import androidx.compose.*
import androidx.ui.animation.Crossfade
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.List
import androidx.ui.material.ripple.ripple
import androidx.ui.unit.dp
import com.example.tvh.di.IAppContainer
import com.example.tvh.services.Navigator
import com.example.tvh.ui.article.ArticleScreen
import com.example.tvh.ui.auditInfo.AuditInfoScreen
import com.example.tvh.ui.common.ArticleActions
import com.example.tvh.ui.home.HomeScreen

@Composable
fun App(appContainer: IAppContainer) {
    val navigator = appContainer.navigator
    val (scaffoldState, setScaffoldState) = state { ScaffoldState() }
    MaterialTheme(
        colors = lightThemeColors,
        typography = themeTypography
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                AppDrawer(
                    currentScreen = navigator.getCurrentScreen(),
                    onClose = { setScaffoldState(ScaffoldState(DrawerState.Closed)) },
                    onNavigateTo = { navigator.navigateTo(it) }
                )
            },
            topAppBar = {
                TopAppBar(
                    title = { Text("Прихожанин (Админ)") },
                    navigationIcon = {
                        IconButton(
                            onClick = { setScaffoldState(ScaffoldState(DrawerState.Opened)) }
                        ) {
                            Icon(Icons.Filled.List)
                        }
                    }
                )
            },
            bodyContent = {
                Crossfade(navigator.getCurrentScreen()) { screen ->
                    when (screen) {
                        is Navigator.Screen.HomeScreen -> HomeScreen(appContainer)
                        is Navigator.Screen.ArticleScreen -> ArticleScreen(
                            article = screen.article,
                            appContainer = appContainer
                        )
                        is Navigator.Screen.AuditInfoScreen -> AuditInfoScreen(appContainer)
                    }
                }
            },
            bottomAppBar = {
                BottomAppBar(
                    backgroundColor = Color.White,
                    content = {
                        BottomAppBarContent(appContainer)
                    }
                )
            }
        )
    }
}

@Composable
fun BottomAppBarContent(appContainer: IAppContainer) {
    val articleCommander = appContainer.articleCommander
    val navigator = appContainer.navigator
    val componentsWithClipboardManager = appContainer.componentsWithClipboardManager

    Row( modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
        ArticleActions(
            componentsWithClipboardManager = componentsWithClipboardManager,
            onAddArticle = { article ->
                if (article.asNews == 1) {
                    articleCommander.updateNewsArticle(article)
                } else {
                    articleCommander.add(article)
                }
            }
        )
    }
}
