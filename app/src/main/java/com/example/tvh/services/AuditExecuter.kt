package com.example.tvh.services

import com.example.tvh.dao.AuditDao
import com.example.tvh.entity.Audit
import com.example.tvh.entity.AuditType
import kotlin.reflect.KClass

interface IAuditExecutor {
    fun <T : Any>runToCreate(entityClass: KClass<T>, command: () -> String, callback: () -> Unit)
    fun <T : Any>runToDelete(entityClass: KClass<T>, command: () -> String, callback: () -> Unit)
    fun <T : Any>runToUpdate(entityClass: KClass<T>, command: () -> String, callback: () -> Unit)
}

class AuditExecutor(
    private val auditDao: AuditDao,
    private val executor: AppExecutor,
    private val auditDocManager: IAuditDocManager
) : IAuditExecutor {
    private fun runWithAuditCreating(
        command: () -> String,
        audit: (entityUid: String) -> List<Audit>,
        callback: (List<Audit>) -> Unit
    ) {
        var audits = emptyList<Audit>()
        var entityUid = ""
        executor.run({
            entityUid = command()
            audits = audit(entityUid)
            audits.forEach { auditDao.create(it) }
        }, {
            if (entityUid.isEmpty()) {
                throw Error("Errors during command execution")
            }
            callback(audits)
        })
    }

    override fun <T : Any>runToCreate(entityClass: KClass<T>, command: () -> String, callback: () -> Unit) {
        runWithAuditCreating(
            command,
            audit = { entityUid ->
                listOf(
                    Audit(
                        entityUid = entityUid,
                        entityType = entityClass.java.simpleName,
                        type = AuditType.CREATE.toString()
                    )
                )
            }
        ) { audits ->
            auditDocManager.pushWithEntityJson(audits.first()) {}
            callback()
        }
    }

    override fun <T: Any>runToDelete(entityClass: KClass<T>, command: () -> String, callback: () -> Unit) {
        runWithAuditCreating(
            command,
            audit = { entityUid ->
                listOf(
                    Audit(
                        entityUid = entityUid,
                        entityType = entityClass.java.simpleName,
                        type = AuditType.DELETE.toString()
                    )
                )
            }
        ) { audits ->
            auditDocManager.push(audits.first()) {}
            callback()
        }
    }

    override fun <T: Any>runToUpdate(entityClass: KClass<T>, command: () -> String, callback: () -> Unit) {
        runWithAuditCreating(
            command,
            audit = { entityUid ->
                listOf(
                    Audit(
                        entityUid = entityUid,
                        entityType = entityClass.java.simpleName,
                        type = AuditType.DELETE.toString()
                    ),
                    Audit(
                        entityUid = entityUid,
                        entityType = entityClass.java.simpleName,
                        type = AuditType.CREATE.toString()
                    )
                )
            }
        ) { audits ->
            auditDocManager.push(audits.first()) {
                auditDocManager.pushWithEntityJson(audits.last()) {}
            }
            callback()
        }
    }
}
