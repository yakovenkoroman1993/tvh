package com.example.tvh.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvh.dao.AuditDao
import com.example.tvh.dao.ArticleDao
import com.example.tvh.entity.Audit
import com.example.tvh.entity.Article
import com.example.tvh.getDatabaseMigrations

@Database(
    entities = [
        Article::class,
        Audit::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun auditDao(): AuditDao

    companion object {
        private const val dbName = "tvh34"

        fun create(applicationContext: Context): AppDatabase {
            return Room
                .databaseBuilder(applicationContext, AppDatabase::class.java, dbName)
                .addMigrations(*getDatabaseMigrations())
                .build()
        }

        fun isDatabaseExisted(applicationContext: Context) : Boolean {
            val dbFile = applicationContext.getDatabasePath(dbName)
            return dbFile.exists()
        }
    }
}
