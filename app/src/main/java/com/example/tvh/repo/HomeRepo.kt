package com.example.tvh.repo

import com.example.tvh.model.UiModel
import com.example.tvh.model.Home
import com.example.tvh.services.AppDatabase
import com.example.tvh.services.AppExecutor

class HomeRepo(
    private val db: AppDatabase,
    private val ui: UiModel,
    private val executor: AppExecutor
) {
    fun loadHome() {
        executor.run({ db.articleDao().findAll() }) {
            ui.home = Home(articles = it)
        }
    }
}