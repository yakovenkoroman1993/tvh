package com.example.tvh

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                CREATE TABLE `Audit` (
                    uid INTEGER PRIMARY KEY NOT NULL,
                    type TEXT NOT NULL,
                    entity_type TEXT NOT NULL,
                    entity_uid INTEGER NOT NULL,
                    created_at TEXT NOT NULL,
                    updated_at TEXT NOT NULL,
                    published INTEGER NOT NULL DEFAULT 0
                )
            """.trimIndent()
        )
    }
}