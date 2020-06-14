package com.example.tvh.services

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.HashMap

interface IRemoteDatabase {
    // TODO: Remove this method. Replace to push
    @Deprecated("This method will be removed")
    fun collection(name: String): CollectionReference

    fun push(collectionName: String, docUid: String, entity: Any? = null, callback: () -> Unit = {})
    fun push(collectionName: String, docUid: String, map: HashMap<String, Any>? = null, callback: () -> Unit = {})
}

class RemoteDatabase(applicationContext: Context) : IRemoteDatabase {
    enum class Collections {
        Audit,
        Article
    }

    companion object {
        val gson = Gson()
    }

    private var db: FirebaseFirestore

    init {
        FirebaseApp.initializeApp(applicationContext)
        db = Firebase.firestore
    }

    override fun collection(name: String): CollectionReference {
        return db.collection(name)
    }

    override fun push(
        collectionName: String,
        docUid: String,
        entity: Any?,
        callback: () -> Unit
    ) {
        val ref = db.collection(collectionName).document(docUid)
        if (entity == null) {
            ref.delete()
        } else {
            ref.set(
                gson.fromJson(
                    gson.toJson(entity),
                    object : TypeToken<HashMap<String?, Any?>?>() {}.type
                )
            )
        }
            .addOnSuccessListener { callback() }
            .addOnFailureListener {
                it.printStackTrace()
                callback()
            }
    }

    override fun push(
        collectionName: String,
        docUid: String,
        map: HashMap<String, Any>?,
        callback: () -> Unit
    ) {
        val ref = db.collection(collectionName).document(docUid)
        if (map == null) {
            ref.delete()
        } else {
            ref.set(map)
        }
            .addOnSuccessListener { callback() }
            .addOnFailureListener {
                it.printStackTrace()
                callback()
            }
    }
}