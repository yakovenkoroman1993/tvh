package com.example.tvh.ui

import androidx.compose.*
//import androidx.ui.material.Scaffold
import androidx.ui.animation.Crossfade
import androidx.ui.material.DrawerState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.vectorResource
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
//        Scaffold(
//            scaffoldState = scaffoldState,
//            drawerContent = {
//                AppDrawer(
//                    currentScreen = Screen.Home,
//                    closeDrawer = { scaffoldState.drawerState = DrawerState.Closed }
//                )
//            },
//            topAppBar = {
//                TopAppBar(
//                    title = { Text(text = "Jetnews") },
//                    navigationIcon = {
//                        IconButton(onClick = { scaffoldState.drawerState = DrawerState.Opened }) {
//                            Icon(vectorResource(R.drawable.ic_jetnews_logo))
//                        }
//                    }
//                )
//            },
//            bodyContent = { /*modifier ->*/
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

//                HomeScreenContent(postsRepository, modifier)
//            }
//        )
    }
}
