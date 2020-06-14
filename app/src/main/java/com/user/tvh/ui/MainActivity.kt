package com.user.tvh.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.user.tvh.TvhApplication
import com.user.tvh.services.Navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (this.application as TvhApplication).appContainer
        setContent { App(appContainer) }
    }

    override fun onBackPressed() {
        val appContainer = (this.application as TvhApplication).appContainer
        val navigator = appContainer.navigator
        when (navigator.getCurrentScreen()) {
            is Navigator.Screen.ArticleScreen -> navigator.navigateTo(Navigator.Screen.HomeScreen)
            else -> super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val appContainer = (this.application as TvhApplication).appContainer
        appContainer.destroy()
    }
}
