package com.example.tvh.services

import com.example.tvh.entity.Audit
import com.google.firebase.firestore.ListenerRegistration
import com.google.gson.Gson
import java.util.*
import kotlin.collections.HashMap

interface IAuditDocManager {
    fun subscribe()
    fun unsubscribe()
    fun pushWithEntityJson(audit: Audit, callback: () -> Unit)
    fun push(audit: Audit, callback: () -> Unit)
}

class AuditDocManager(
    private val db: AppDatabase,
    private val rdb: IRemoteDatabase,
    private val executor: AppExecutor,
    private val device: IDeviceInfoProvider
) : IAuditDocManager {
    private lateinit var subscription: ListenerRegistration

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

    private fun markAuditAsPublished(audit: Audit, callback: () -> Unit = {}) {
        executor.run({
            db.auditDao().update(audit.copy(published = true))
        }) {
            callback()
        }
    }

    private fun getDocument(audit: Audit): HashMap<String, Any> {
        return hashMapOf(
            "type" to audit.type,
            "entity_type" to audit.entityType,
            "entity_id" to audit.entityUid,
            "device_uid" to device.uid
        )
    }

    private fun push(audit: Audit, auditDoc: HashMap<String, Any>, callback: () -> Unit = {}) {
        executor.run({
            rdb
                .collection(RemoteDatabase.Collections.Audit.name)
                .add(auditDoc)
                .addOnSuccessListener { markAuditAsPublished(audit, callback) }
                .addOnFailureListener { print(it.message) }
        })
    }

    override fun push(audit: Audit, callback: () -> Unit) {
        val auditDoc = getDocument(audit)
        push(audit, auditDoc, callback)
    }

    override fun pushWithEntityJson(audit: Audit, callback: () -> Unit) {
        val auditDoc = getDocument(audit)
        getEntityJson(audit) { json ->
            auditDoc["entity_json"] = json
            push(audit, auditDoc, callback ?: {})
        }
    }

    override fun subscribe() {
        subscription = rdb
            .collection(RemoteDatabase.Collections.Audit.name)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    print(error.message)
                    return@addSnapshotListener
                }
                // todo: implement Entity creating by audit documents
            }
    }

    override fun unsubscribe() {
        subscription.remove()
    }
}