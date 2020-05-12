package com.example.tvh.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.example.tvh.TodoListApplication
import com.example.tvh.services.Navigator
import com.example.tvh.ui.article.ArticleScreen
import com.example.tvh.ui.home.HomeScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (this.application as TodoListApplication).container
        val navigator = appContainer.navigator
        setContent {
            App(navigator.getCurrentScreen()) { screen ->
                when (screen) {
                    is Navigator.Screen.Home -> HomeScreen(
                        appContainer = appContainer
                    )
                    is Navigator.Screen.Article -> ArticleScreen(
                        title = "title",
                        text = "body",
                        appContainer = appContainer
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        val appContainer = (this.application as TodoListApplication).container
        val navigator = appContainer.navigator
        when (navigator.getCurrentScreen()) {
            is Navigator.Screen.Article -> navigator.navigateTo(Navigator.Screen.Home)
            else -> super.onBackPressed()
        }
    }
}
