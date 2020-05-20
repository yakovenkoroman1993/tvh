package com.example.tvh.services

import androidx.compose.Model
import com.example.tvh.entity.Group

class Navigator {
    sealed class Screen {
        object HomeScreen: Screen()
        class GroupScreen(val group : Group): Screen()
        object AuditInfoScreen: Screen()
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
