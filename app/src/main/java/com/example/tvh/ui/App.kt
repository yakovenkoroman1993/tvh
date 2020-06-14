package com.example.tvh.ui

import androidx.compose.*
import androidx.ui.animation.Crossfade
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.ripple
import androidx.ui.unit.dp
import com.example.tvh.di.IAppContainer
import com.example.tvh.services.Navigator
import com.example.tvh.ui.article.ArticleScreen
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
            topAppBar = {
                TopAppBar(title = { Text("Прихожанин") })
            },
            bodyContent = {
                Crossfade(navigator.getCurrentScreen()) { screen ->
                    when (screen) {
                        is Navigator.Screen.HomeScreen -> HomeScreen(appContainer)
                        is Navigator.Screen.ArticleScreen -> ArticleScreen(
                            article = screen.article,
                            appContainer = appContainer
                        )
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
    Row( modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
        Button(
            modifier = Modifier.preferredWidthIn(maxWidth = 200.dp).ripple(),
            onClick = {}
        ) {
            Text("Задать вопрос")
        }
    }
}
