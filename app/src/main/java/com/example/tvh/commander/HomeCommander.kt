package com.example.tvh.commander

import com.example.tvh.entity.Group
import com.example.tvh.repo.HomeRepo
import com.example.tvh.services.AuditExecutor
import com.example.tvh.services.AppDatabase

class HomeCommander(
    private val db: AppDatabase,
    private val repo: HomeRepo,
    private val executor: AuditExecutor
) {
    fun addGroup(g: Group) {
        executor.runToCreate(Group::class, {
            db.groupDao().create(
                g.copy(uid = 0)
            )
            db.groupDao().lastUid()
        }) {
            repo.loadHome()
        }
    }

    fun removeGroup(g: Group) {
        executor.runToDelete(Group::class, {
            db.groupDao().delete(g)
            g.uid
        }) {
            repo.loadHome()
        }
    }

}