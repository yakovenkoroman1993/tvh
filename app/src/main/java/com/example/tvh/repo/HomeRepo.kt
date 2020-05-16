package com.example.tvh.repo

import com.example.tvh.model.UiModel
import com.example.tvh.model.Home
import com.example.tvh.services.Database
import com.example.tvh.services.Executor

class HomeRepo(
    private val db: Database,
    private val ui: UiModel,
    private val executor: Executor
) {
    fun loadHome() {
        executor.run({ db.groupDao().findAll() }) {
            ui.home = Home(groups = it)
        }
    }
}