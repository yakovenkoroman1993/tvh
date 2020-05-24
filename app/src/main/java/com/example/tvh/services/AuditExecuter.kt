package com.example.tvh.services

import com.example.tvh.dao.AuditDao
import com.example.tvh.entity.Audit
import com.example.tvh.entity.AuditType
import kotlin.reflect.KClass

interface IAuditExecutor {
    fun <T : Any>runToCreate(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit)
    fun <T : Any>runToDelete(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit)
    fun <T : Any>runToUpdate(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit)
}

class AuditExecutor(
    private val auditDao: AuditDao,
    private val executor: AppExecutor,
    private val auditDocManager: IAuditDocManager
) : IAuditExecutor {
    private fun runWithAuditCreating(
        command: () -> Long,
        audit: (entityUid: Int) -> Array<Audit>,
        callback: (List<Audit>) -> Unit
    ) {
        var entityUid = 0
        var uids = emptyList<Long>()
        executor.run({
            entityUid = command().toInt()
            uids = auditDao.create(*audit(entityUid))
        }, {
            if (entityUid.compareTo(0) == 0) {
                throw Error("Errors during command execution")
            }
            val audits = audit(entityUid).mapIndexed { index, audit ->
                audit.copy(uid = uids[index].toInt())
            }
            callback(audits)
        })
    }

    override fun <T : Any>runToCreate(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit) {
        runWithAuditCreating(
            command,
            audit = { entityUid ->
                arrayOf(
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

    override fun <T: Any>runToDelete(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit) {
        runWithAuditCreating(
            command,
            audit = { entityUid ->
                arrayOf(
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

    override fun <T: Any>runToUpdate(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit) {
        runWithAuditCreating(
            command,
            audit = { entityUid ->
                arrayOf(
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
