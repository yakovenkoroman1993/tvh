package com.example.tvh.services

import com.example.tvh.entity.Audit
import com.example.tvh.entity.AuditType
import kotlin.reflect.KClass

class AuditExecutor(
    private val db: AppDatabase,
    private val executor: Executor
) {
    private fun runInThreads(command: () -> Int, callback: () -> Unit, audits: (uid: Int) -> Unit) {
        executor.run({
            val uid = command()
            audits(uid)
        }, {
            callback()
        })
    }

    fun <T : Any>runToCreate(entityClass: KClass<T>, command: () -> Int, callback: () -> Unit) {
        runInThreads(command, callback) { uid ->
            db.auditDao().create(
                Audit(
                    entityUid = uid,
                    entityType = entityClass.java.simpleName,
                    type = AuditType.CREATE.toString()
                )
            )
        }
    }

    fun <T: Any>runToDelete(entityClass: KClass<T>, command: () -> Int, callback: () -> Unit) {
        runInThreads(command, callback) { uid ->
            db.auditDao().create(
                Audit(
                    entityUid = uid,
                    entityType = entityClass.java.simpleName,
                    type = AuditType.DELETE.toString()
                )
            )
        }
    }

    fun <T: Any>runToUpdate(entityClass: KClass<T>, command: () -> Int, callback: () -> Unit) {
        runInThreads(command, callback) { uid ->
            db.auditDao().create(
                Audit(
                    entityUid = uid,
                    entityType = entityClass.java.simpleName,
                    type = AuditType.DELETE.toString()
                ),
                Audit(
                    entityUid = uid,
                    entityType = entityClass.java.simpleName,
                    type = AuditType.CREATE.toString()
                )
            )
        }
    }
}
