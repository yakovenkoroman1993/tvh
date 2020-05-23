package com.example.tvh.services

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RemoteDatabase(applicationContext: Context) {
    enum class Collections {
        Audit
    }

    private var db: FirebaseFirestore

    init {
        FirebaseApp.initializeApp(applicationContext)
        db = Firebase.firestore
    }

    fun collection(name: String): CollectionReference {
        return db.collection(name)
    }
}