package com.example.tvh.repo

import com.example.tvh.model.AuditInfo
import com.example.tvh.model.UiModel
import com.example.tvh.services.AppDatabase
import com.example.tvh.services.Executor

class AuditInfoRepo(
    private val db: AppDatabase,
    private val ui: UiModel,
    private val executor: Executor
) {
    fun loadAudit() {
        executor.run({ db.auditDao().findAll() }) {
            ui.auditInfo = AuditInfo(logs = it)
        }
    }
}