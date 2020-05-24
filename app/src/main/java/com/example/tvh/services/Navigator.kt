package com.example.tvh.services

import androidx.compose.Model
import com.example.tvh.entity.Group

interface INavigator {
    fun getCurrentScreen(): Navigator.Screen
    fun navigateTo(destination: Navigator.Screen)
}

@Model
private class Navigation {
    var currentScreen: Navigator.Screen = Navigator.Screen.HomeScreen
}

class Navigator : INavigator {
    sealed class Screen {
        object HomeScreen: Screen()
        class GroupScreen(val group : Group): Screen()
        object AuditInfoScreen: Screen()
    }

    private val navigation: Navigation = Navigation()

    override fun getCurrentScreen(): Screen {
        return this.navigation.currentScreen
    }

    override fun navigateTo(destination: Screen) {
        this.navigation.currentScreen = destination
    }
}
