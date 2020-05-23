package com.example.tvh.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvh.dao.AuditDao
import com.example.tvh.dao.GroupDao
import com.example.tvh.entity.Audit
import com.example.tvh.entity.Group
import com.example.tvh.getDatabaseMigrations

@Database(
    entities = [
        Group::class,
        Audit::class
    ],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun auditDao(): AuditDao

    companion object {
        private const val dbName = "tvh"

        fun create(applicationContext: Context): AppDatabase {
            return Room
                .databaseBuilder(applicationContext, AppDatabase::class.java, dbName)
                .addMigrations(*getDatabaseMigrations())
                .build()
        }
    }
}
