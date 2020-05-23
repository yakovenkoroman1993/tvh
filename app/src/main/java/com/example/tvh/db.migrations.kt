package com.example.tvh

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

private class Migration1To2 : Migration(1, 2) {
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

private class Migration2To3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val defaultDate = Date().toString()
        database.execSQL(
            """
                ALTER TABLE `Group` ADD COLUMN created_at TEXT NOT NULL DEFAULT "$defaultDate" 
            """.trimIndent()
        )
        database.execSQL(
            """
                ALTER TABLE `Group` ADD COLUMN updated_at TEXT NOT NULL DEFAULT "$defaultDate"
            """.trimIndent()
        )
    }
}

private class Migration3To4 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val defaultDateTime = Date().time.toString()
        database.execSQL(
            """
                UPDATE `Group` SET created_at = "$defaultDateTime", updated_at = "$defaultDateTime"  
            """.trimIndent()
        )
        database.execSQL(
            """
                UPDATE `Audit` SET created_at = "$defaultDateTime", updated_at = "$defaultDateTime" 
            """.trimIndent()
        )
    }
}

fun getDatabaseMigrations(): Array<Migration> {
    return arrayOf(
        Migration1To2(),
        Migration2To3(),
        Migration3To4()
        // ...
    )
}