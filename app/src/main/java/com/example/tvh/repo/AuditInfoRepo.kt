package com.example.tvh.repo

import com.example.tvh.model.AuditInfo
import com.example.tvh.model.UiModel
import com.example.tvh.services.AppDatabase
import com.example.tvh.services.AppExecutor

class AuditInfoRepo(
    private val db: AppDatabase,
    private val ui: UiModel,
    private val executor: AppExecutor
) {
    fun loadAudit() {
        executor.run({ db.auditDao().findAll() }) {
            ui.auditInfo = AuditInfo(logs = it)
        }
    }
}