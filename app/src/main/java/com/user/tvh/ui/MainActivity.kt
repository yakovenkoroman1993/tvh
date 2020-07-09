package com.user.tvh.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.user.tvh.TvhApplication
import com.user.tvh.entity.Article
import com.user.tvh.model.Home
import com.user.tvh.services.Navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (this.application as TvhApplication).appContainer
        setContent { App(appContainer) }

        subscribeToArticles()
    }

    private fun subscribeToArticles() {
        val appContainer = (this.application as TvhApplication).appContainer
        val rdb = appContainer.rdb
        val ui = appContainer.ui
        val notifier = appContainer.notifier

        rdb.subscribe(Article::class.java) { articles, addedArticles ->
            val firstLoading = ui.home == null
            ui.home = Home(articles = articles)
            if (!firstLoading && addedArticles.isNotEmpty()) {
                addedArticles.forEach {
                    notifier.push(
                        title = it.name,
                        message = if (it.desc.length > 100) it.desc.substring(0, 100) + "..." else it.desc
                    )
                }
            }
        }
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
