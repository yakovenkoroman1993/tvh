package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.compose.onActive
import com.example.tvh.di.IAppContainer

@Composable
fun HomeScreen(IAppContainer: IAppContainer) {
    val repo = IAppContainer.homeRepo
    val commander = IAppContainer.homeCommander
    val navigator = IAppContainer.navigator

    onActive {
        repo.loadHome()
    }

    Home(
        groups = IAppContainer.ui.home.groups,
        onAddGroup = {
            commander.addGroup(it)
        },
        onRemoveGroup = { commander.removeGroup(it) },
        onNavigateToGroup = { navigator.navigateTo(it) }
    )
}

