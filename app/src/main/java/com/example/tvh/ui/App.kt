package com.example.tvh.ui

import androidx.compose.*
import androidx.ui.animation.Crossfade
import androidx.ui.material.MaterialTheme
import com.example.tvh.di.AppContainer
import com.example.tvh.services.Navigator
import com.example.tvh.ui.group.GroupScreen
import com.example.tvh.ui.home.HomeScreen

@Composable
fun App(appContainer: AppContainer) {
    val navigator = appContainer.navigator

    MaterialTheme(
        colors = lightThemeColors,
        typography = themeTypography
    ) {
        Crossfade(navigator.getCurrentScreen()) { screen ->
            when (screen) {
                is Navigator.Screen.HomeScreen -> HomeScreen(
                    appContainer = appContainer
                )
                is Navigator.Screen.GroupScreen -> GroupScreen(
                    title = screen.group.name,
                    text = screen.group.uid.toString(),
                    appContainer = appContainer
                )
            }
        }
    }
}
