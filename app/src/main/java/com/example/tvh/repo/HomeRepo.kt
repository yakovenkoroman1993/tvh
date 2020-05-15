package com.example.tvh.repo

import com.example.tvh.model.UiModel
import com.example.tvh.model.Home
import com.example.tvh.services.Database

class HomeRepo(
    private val db: Database,
    private val ui: UiModel
) {
    fun loadHome() {
        val groups = db.groupDao().findAll()
        ui.home = Home(groups = groups)
    }
}