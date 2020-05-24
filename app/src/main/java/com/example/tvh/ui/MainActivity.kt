package com.example.tvh.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.example.tvh.TvhApplication
import com.example.tvh.services.Navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (this.application as TvhApplication).containerI
        setContent { App(appContainer) }
    }

    override fun onBackPressed() {
        val appContainer = (this.application as TvhApplication).containerI
        val navigator = appContainer.navigator
        when (navigator.getCurrentScreen()) {
            is Navigator.Screen.GroupScreen -> navigator.navigateTo(Navigator.Screen.HomeScreen)
            else -> super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val appContainer = (this.application as TvhApplication).containerI
        appContainer.destroy()
    }
}
