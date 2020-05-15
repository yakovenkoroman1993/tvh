package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.compose.onActive
import androidx.compose.state
import androidx.compose.unaryPlus
import com.example.tvh.di.AppContainer

@Composable
fun HomeScreen(appContainer: AppContainer) {
    val repo = appContainer.homeRepo
    val commander = appContainer.homeCommander
    val navigator = appContainer.navigator

//    +state { appContainer.ui.home }
    +onActive {
        repo.loadHome()
    }

    Home(
        groups = appContainer.ui.home.groups,
        onAddGroup = {
            commander.addGroup(it)
        },
        onRemoveGroup = { commander.removeGroup(it) },
        onNavigateToGroup = { navigator.navigateTo(it) }
    )
}

