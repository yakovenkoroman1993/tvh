package com.user.tvh.ui.home

import androidx.compose.Composable
import androidx.compose.onActive
import com.user.tvh.di.IAppContainer

@Composable
fun HomeScreen(appContainer: IAppContainer) {
    val repo = appContainer.homeRepo
    val navigator = appContainer.navigator
    val imageLoader = appContainer.imageLoader

    onActive {
        repo.loadHome()
    }

    Home(
        articles = appContainer.ui.home?.articles ?:  emptyList(),
        imageLoader = imageLoader,
        onNavigateToArticle = { navigator.navigateTo(it) },
        onNavigateToSite = { navigator.navigateToSite(it) }
    )
}

