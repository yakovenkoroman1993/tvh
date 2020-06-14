package com.example.tvh.services

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.Model
import androidx.core.content.ContextCompat.startActivity
import com.example.tvh.entity.Article

interface INavigator {
    fun getCurrentScreen(): Navigator.Screen
    fun navigateTo(destination: Navigator.Screen)
    fun navigateToSite(url: String)
}

@Model
private class Navigation {
    var currentScreen: Navigator.Screen = Navigator.Screen.HomeScreen
}

class Navigator(private val context: Context) : INavigator {
    sealed class Screen {
        object HomeScreen: Screen()
        class ArticleScreen(val article : Article): Screen()
    }

    private val navigation: Navigation = Navigation()

    override fun getCurrentScreen(): Screen {
        return this.navigation.currentScreen
    }

    override fun navigateTo(destination: Screen) {
        this.navigation.currentScreen = destination
    }

    override fun navigateToSite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(context, intent, null)
    }


}
