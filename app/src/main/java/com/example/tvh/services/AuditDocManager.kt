package com.example.tvh.services

import com.example.tvh.entity.Audit
import com.example.tvh.entity.AuditType
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import java.util.*
import kotlin.collections.HashMap

interface IAuditDocManager {
    fun subscribe()
    fun pushWithEntityJson(audit: Audit, callback: () -> Unit)
    fun push(audit: Audit, callback: () -> Unit)
    fun destroy()
}

class AuditDocManager(
    private val db: AppDatabase,
    private val rdb: IRemoteDatabase,
    private val executor: AppExecutor,
    private val device: IDeviceInfoProvider
) : IAuditDocManager {

    private var lockSubscription = false

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
            "entity_uid" to audit.entityUid,
            "created_at" to audit.createdAt,
            "device" to hashMapOf(
                device.uid to true
            )
        )
    }

    private fun push(audit: Audit, auditDoc: HashMap<String, Any>, callback: () -> Unit = {}) {
        rdb.push(
            collectionName = RemoteDatabase.Collections.Audit.name,
            docUid = audit.uid,
            map = auditDoc
        ) {
            markAuditAsPublished(audit, callback)
        }
    }

    override fun push(audit: Audit, callback: () -> Unit) {
        val auditDoc = getDocument(audit)
        push(audit, auditDoc, callback)
    }

    override fun pushWithEntityJson(audit: Audit, callback: () -> Unit) {
        val auditDoc = getDocument(audit)
        getEntityJson(audit) { json ->
            auditDoc["entity_json"] = json
            push(audit, auditDoc, callback)
        }
    }

    override fun subscribe() {
//        subscription = rdb
//            .collection(RemoteDatabase.Collections.Audit.name)
//            .addSnapshotListener { snapshot, error ->
//                if (lockSubscription) {
//                    return@addSnapshotListener
//                }
//                lockSubscription = true
//                if (error != null) {
//                    print(error.message)
//                    return@addSnapshotListener
//                }
//                if (snapshot == null || snapshot.documents.isEmpty()) {
//                    return@addSnapshotListener
//                }
//
//                executor.run({
//                    rdb
//                        .collection(RemoteDatabase.Collections.Audit.name)
//                        .orderBy("created_at", Query.Direction.ASCENDING)
//                        .limit(100)
//                        .get()
//                        .addOnSuccessListener {
//                            sync(it.documents)
//                        }
//                        .addOnFailureListener { it.printStackTrace() }
//                })
//            }
    }

    private fun sync(documents: List<DocumentSnapshot>) {
        executor.run({
            val exclusiveDocUids = getExclusiveDocUids(documents)
            val documentsToMarkAsSynchronized = mutableListOf<DocumentSnapshot>()
            documents.forEach { docSnapshot ->
                val doc = docSnapshot.data ?: return@forEach
                try {
                    val docDevice = doc["device"] as HashMap<String, Boolean>
                    val isDocSynchronized = docDevice.containsKey(device.uid) && docDevice[device.uid] == true
                    if (isDocSynchronized) {
                        return@forEach
                    }

                    if (exclusiveDocUids.contains(docSnapshot.id)) {
                        documentsToMarkAsSynchronized.add(docSnapshot)
                        return@forEach
                    }

                    syncDatabase(doc as HashMap<String, Any>)
                    documentsToMarkAsSynchronized.add(docSnapshot)
                }
                catch (e: Exception) {
                    print(e.message)
                    documentsToMarkAsSynchronized.add(docSnapshot)
                }
            }

            markDocsAsSynchronized(documentsToMarkAsSynchronized) {
                lockSubscription = false
            }
        })
    }

    private fun markDocsAsSynchronized(documents: List<DocumentSnapshot>, callback: () -> Unit) {
        if (documents.isEmpty()) {
            callback()
            return
        }

        var i = 0
        fun asyncForeach(docSnapshot: DocumentSnapshot) {
            markDocAsSynchronized(docSnapshot.id, docSnapshot.data as HashMap<String, Any>) {
                i++
                if (i < documents.size) {
                    asyncForeach(documents[i])
                } else {
                    callback()
                }
            }
        }
        asyncForeach(documents[i])
    }

    private fun markDocAsSynchronized(docId: String, doc: HashMap<String, Any>, callback: () -> Unit) {
        try {
            val docDevice = doc["device"] as HashMap<String, Boolean>
            docDevice[device.uid] = true
            doc["device"] = docDevice
            rdb
                .collection(RemoteDatabase.Collections.Audit.name)
                .document(docId)
                .set(doc)
                .addOnSuccessListener { callback() }
                .addOnFailureListener {
                    it.printStackTrace()
                    callback()
                }
        }
        catch (e: Exception) {
            print(e.message)
            callback()
        }
    }

    private fun getExclusiveDocUids(documents: List<DocumentSnapshot>): List<String> {
        val result = mutableListOf<String>()
        val docUidByCreatedEntityUidMap = hashMapOf<String, String>()
        documents.forEach { docSnapshot ->
            val doc = docSnapshot.data ?: return@forEach
            val auditType = doc["type"] as String
            val entityUid = doc["entity_uid"] as String
            val docDevice = doc["device"] as HashMap<String, Boolean>
            val isDocSynchronized = docDevice.containsKey(device.uid) && docDevice[device.uid] == true

            if (docUidByCreatedEntityUidMap.containsKey(entityUid)) {
                if (auditType == AuditType.DELETE.name && !isDocSynchronized) {
                    result.add(docSnapshot.id)
                    docUidByCreatedEntityUidMap[entityUid]?.let { result.add(it) }
                }
            } else {
                if (auditType == AuditType.CREATE.name && !isDocSynchronized) {
                    docUidByCreatedEntityUidMap[entityUid] = docSnapshot.id
                }
            }
        }

        return result
    }

    private fun syncDatabase(doc: HashMap<String, Any>) {
        val entityType = doc["entity_type"] as String
        val daoMethodName = entityType.toLowerCase(Locale.ROOT) + "Dao"
        val dao = db.javaClass.getMethod(daoMethodName).invoke(db)
        when (doc["type"] as String) {
            AuditType.CREATE.name -> {
                val entityJson = doc["entity_json"] as String
                val entityClass = Class.forName("com.example.tvh.entity.$entityType")
                val entity = Gson().fromJson(entityJson, entityClass)
                (dao.javaClass.methods.find { it.name == "create" })?.invoke(dao, entity)
            }
            AuditType.DELETE.name -> {
                val entityUid = doc["entity_uid"] as String
                val entity = (dao.javaClass.methods.find { it.name == "find" })
                    ?.invoke(dao, entityUid)
                    ?: return

                (dao.javaClass.methods.find { it.name == "delete" })?.invoke(dao, entity)
            }
            else -> throw Error("Unknown audit type from obtained document")
        }
    }

    override fun destroy() {
        subscription.remove()
    }

}