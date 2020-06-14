package com.user.tvh.repo

import com.user.tvh.entity.Article
import com.user.tvh.model.UiModel
import com.user.tvh.model.Home
import com.user.tvh.services.RemoteDatabase
import com.google.firebase.firestore.ListenerRegistration

class HomeRepo(
    private val rdb: RemoteDatabase,
    private val ui: UiModel
) {
    fun loadHome() {
        rdb.findAll(Article::class.java) {
            ui.home = Home(articles = it)
        }
    }

    fun subscribeToArticles(): ListenerRegistration {
        return rdb.subscribe(Article::class.java) {
            ui.home = Home(articles = it)
        }
    }
}