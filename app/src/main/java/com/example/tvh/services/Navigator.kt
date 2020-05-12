package com.example.tvh.services

import androidx.compose.Model

class Navigator {
    sealed class Screen {
        object HomeScreen : Screen()
        class GroupScreen(val group : com.example.tvh.model.Group) : Screen()
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
    var currentScreen: Navigator.Screen = Navigator.Screen.HomeScreen
}
