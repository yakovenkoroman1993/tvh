package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.compose.onActive
import androidx.compose.state
import androidx.compose.unaryPlus
import com.example.tvh.data.SourceData
import com.example.tvh.di.AppContainer

@Composable
fun HomeScreen(appContainer: AppContainer) {
    val loader = appContainer.loader
    val commander = appContainer.commander
    val navigator = appContainer.navigator

    val sourceData = +state { SourceData() }
    +onActive {
        sourceData.value.home = loader.getHome()
    }

    Home(
        groups = sourceData.value.home.groups,
        onAddGroup = { name ->
            commander.addHomeGroup(name)
            sourceData.value.home = loader.getHome()
        },
        onRemoveGroup = {
            commander.removeHomeGroup(it)
            sourceData.value.home = loader.getHome()
        },
        onNavigateToGroup = { screen ->
            navigator.navigateTo(screen)
        }
    )
}

