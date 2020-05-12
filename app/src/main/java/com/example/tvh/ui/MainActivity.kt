package com.example.tvh.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.example.tvh.TvhApplication
import com.example.tvh.services.Navigator
import com.example.tvh.ui.group.GroupScreen
import com.example.tvh.ui.home.HomeScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (this.application as TvhApplication).container
        val navigator = appContainer.navigator
        setContent {
            App(navigator.getCurrentScreen()) { screen ->
                when (screen) {
                    is Navigator.Screen.HomeScreen -> HomeScreen(
                        appContainer = appContainer
                    )
                    is Navigator.Screen.GroupScreen -> GroupScreen(
                        title = screen.group.name,
                        text = screen.group.id.toString(),
                        appContainer = appContainer
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        val appContainer = (this.application as TvhApplication).container
        val navigator = appContainer.navigator
        when (navigator.getCurrentScreen()) {
            is Navigator.Screen.GroupScreen -> navigator.navigateTo(Navigator.Screen.HomeScreen)
            else -> super.onBackPressed()
        }
    }
}
