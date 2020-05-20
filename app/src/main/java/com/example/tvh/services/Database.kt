package com.example.tvh.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tvh.Migration1To2
import com.example.tvh.dao.AuditDao
import com.example.tvh.dao.GroupDao
import com.example.tvh.entity.Audit
import com.example.tvh.entity.Group

@Database(
    entities = [
        Group::class,
        Audit::class
    ],
    version = 2
)
abstract class Database : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun auditDao(): AuditDao

    companion object {
        val MIGRATION_1_2 = Migration1To2()
    }
}
