package com.example.tvh.commander

import com.example.tvh.entity.Group
import com.example.tvh.repo.HomeRepo
import com.example.tvh.services.Database
import com.example.tvh.services.Executor

class HomeCommander(
    private val db: Database,
    private val repo: HomeRepo,
    private val executor: Executor
) {
    fun addGroup(g: Group) {
        executor.run({
            db.groupDao().create(
                g.copy(uid = 0)
            )
        }) {
            repo.loadHome()
        }
    }

    fun removeGroup(g: Group) {
        executor.run({ db.groupDao().delete(g) }) {
            repo.loadHome()
        }
    }

}