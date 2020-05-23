package com.example.tvh.services

import com.example.tvh.entity.Audit
import com.example.tvh.entity.AuditType
import com.google.gson.Gson
import java.util.*
import kotlin.reflect.KClass

class AuditExecutor(
    private val db: AppDatabase,
    private val dbRemote: RemoteDatabase,
    private val executor: Executor,
    private val device: DeviceInfo
) {
    private fun push(audit: Audit, entityJson: String? = null) {
        executor.run({
            val auditDoc = hashMapOf(
                "type" to audit.type,
                "entity_type" to audit.entityType,
                "entity_id" to audit.entityUid,
                "entity_json" to entityJson,
                "device_uid" to device.uid
            )

            dbRemote
                .collection(RemoteDatabase.Collections.Audit.name)
                .add(auditDoc)
                .addOnSuccessListener { markAuditAsPublished(audit) }
                .addOnFailureListener { print(it.message) }
        })
    }

    private fun getEntityJson(audit: Audit, callback: (String) -> Unit) {
        executor.run({
            val methodName = audit.entityType.toLowerCase(Locale.ROOT) + "Dao"
            val dao = db.javaClass.getMethod(methodName).invoke(db)
            val entity = (dao.javaClass.methods.find{ it.name == "find" })?.invoke(dao, audit.entityUid)
            Gson().toJson(entity)
        }) { json ->
            callback(json)
        }
    }

    private fun markAuditAsPublished(audit: Audit) {
        executor.run({
            db.auditDao().update(audit.copy(published = true))
        })
    }

    private fun runWithAuditCreating(
        command: () -> Long,
        audit: (entityUid: Int) -> Array<Audit>,
        callback: (List<Audit>) -> Unit
    ) {
        var entityUid = 0
        var uids = emptyList<Long>()
        executor.run({
            entityUid = command().toInt()
            uids = db.auditDao().create(*audit(entityUid))
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

    fun <T : Any>runToCreate(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit) {
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
            val audit = audits.first()
            getEntityJson(audit) { json ->
                push(audit, json)
            }
            callback()
        }
    }

    fun <T: Any>runToDelete(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit) {
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
            push(audits.first())
            callback()
        }
    }

    fun <T: Any>runToUpdate(entityClass: KClass<T>, command: () -> Long, callback: () -> Unit) {
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
            audits.forEach { audit ->
                getEntityJson(audit) { json ->
                    push(audit, json)
                }
            }
            callback()
        }
    }
}
