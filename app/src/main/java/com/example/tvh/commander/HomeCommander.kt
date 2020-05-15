package com.example.tvh.commander

import com.example.tvh.entity.Group
import com.example.tvh.repo.HomeRepo
import com.example.tvh.services.Database

class HomeCommander(
    private val db: Database,
    private val repo: HomeRepo
) {
    fun addGroup(g: Group) {
        db.groupDao().create(
            g.copy(uid = 0)
        )
        repo.loadHome()
    }

    fun removeGroup(g: Group) {
        db.groupDao().delete(g)
        repo.loadHome()
    }

}