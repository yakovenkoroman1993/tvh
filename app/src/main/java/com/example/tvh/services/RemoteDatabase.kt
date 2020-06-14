package com.example.tvh.services

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface IRemoteDatabase {
    fun <T : Any>findAll(
        objectClass: Class<T>,
        callback: (List<T>) -> Unit = {}
    )
    fun <T : Any>find(
        docUid: String,
        objectClass: Class<T>,
        callback: (T?) -> Unit = {}
    )

    fun <T : Any>subscribe(
        objectClass: Class<T>,
        callback: (List<T>) -> Unit
    ) : ListenerRegistration
}

class RemoteDatabase(applicationContext: Context) : IRemoteDatabase {
    private var db: FirebaseFirestore

    init {
        FirebaseApp.initializeApp(applicationContext)
        db = Firebase.firestore
    }

    override fun <T : Any>subscribe(
        objectClass: Class<T>,
        callback: (List<T>) -> Unit
    ) : ListenerRegistration {
        return db
            .collection(objectClass.simpleName)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }

                if (value == null) {
                    callback(emptyList())
                    return@addSnapshotListener
                }

                callback(value.toObjects(objectClass))
            }
    }

    override fun <T : Any>findAll(
        objectClass: Class<T>,
        callback: (List<T>) -> Unit
    ) {
        db
            .collection(objectClass.simpleName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                callback(querySnapshot.toObjects(objectClass))
            }
            .addOnFailureListener {
                it.printStackTrace()
                callback(emptyList())
            }
    }

    override fun <T : Any>find(
        docUid: String,
        objectClass: Class<T>,
        callback: (T?) -> Unit
    ) {
        db
            .collection(objectClass.simpleName)
            .document(docUid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                callback(querySnapshot.toObject(objectClass))
            }
            .addOnFailureListener {
                it.printStackTrace()
                callback(null)
            }
    }
}