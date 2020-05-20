package com.example.tvh.ui

import androidx.compose.*
import androidx.ui.animation.Crossfade
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.List
import com.example.tvh.di.AppContainer
import com.example.tvh.services.Navigator
import com.example.tvh.ui.group.GroupScreen
import com.example.tvh.ui.auditInfo.AuditInfoScreen
import com.example.tvh.ui.home.HomeScreen

@Composable
fun App(appContainer: AppContainer) {
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
                    title = { Text("TVH") },
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
                        is Navigator.Screen.GroupScreen -> GroupScreen(
                            title = screen.group.name,
                            text = screen.group.uid.toString(),
                            appContainer = appContainer
                        )
                        is Navigator.Screen.AuditInfoScreen -> AuditInfoScreen(appContainer)
                    }
                }
            }
        )
    }
}
