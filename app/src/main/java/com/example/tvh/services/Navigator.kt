package com.example.tvh.services

import androidx.compose.Model

class Navigator {
    sealed class Screen {
        object Home : Screen()
        class Article() : Screen()
    }

    private val navigation: Navigation = Navigation()

    fun getCurrentScreen(): Screen {
        return this.navigation.currentScreen
    }

    fun navigateTo(destination: Screen) {
        this.navigation.currentScreen = destination
    }
}

@Model
private class Navigation {
    var currentScreen: Navigator.Screen = Navigator.Screen.Home
}
