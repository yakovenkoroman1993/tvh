package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.compose.onActive
import com.example.tvh.di.IAppContainer
import com.google.firebase.firestore.ListenerRegistration

@Composable
fun HomeScreen(appContainer: IAppContainer) {
    val repo = appContainer.homeRepo
    val navigator = appContainer.navigator
    val imageLoader = appContainer.imageLoader

    var subscription: ListenerRegistration?
    onActive {
        subscription = repo.subscribeToArticles()
        onDispose {
            subscription!!.remove()
        }
    }

    Home(
        articles = appContainer.ui.home.articles,
        imageLoader = imageLoader,
        onNavigateToArticle = { navigator.navigateTo(it) },
        onNavigateToSite = { navigator.navigateToSite(it) }
    )
}

