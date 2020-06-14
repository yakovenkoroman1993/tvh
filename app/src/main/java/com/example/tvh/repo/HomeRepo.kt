package com.example.tvh.repo

import com.example.tvh.entity.Article
import com.example.tvh.model.UiModel
import com.example.tvh.model.Home
import com.example.tvh.services.RemoteDatabase
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