package com.example.tvh.di

import android.content.Context
import androidx.room.Room
import com.example.tvh.commander.HomeCommander
import com.example.tvh.model.UiModel
import com.example.tvh.repo.AuditInfoRepo
import com.example.tvh.repo.HomeRepo
import com.example.tvh.services.*

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val navigator: Navigator
    val ui: UiModel
    val homeRepo: HomeRepo
    val homeCommander: HomeCommander
    val auditInfoRepo: AuditInfoRepo
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    private val db: Database by lazy {
        Room
            .databaseBuilder(applicationContext, Database::class.java, "tvh")
            .addMigrations(
                Database.MIGRATION_1_2
            )
            .build()
    }

    private val executor: Executor by lazy {
        Executor(ui)
    }

    private val auditExecutor: AuditExecutor by lazy {
        AuditExecutor(
            db = db,
            executor = executor
        )
    }

    override val navigator: Navigator by lazy {
        Navigator()
    }

    override val ui: UiModel by lazy {
        UiModel()
    }

    override val homeRepo: HomeRepo by lazy {
        HomeRepo(
            db = db,
            ui = ui,
            executor = executor
        )
    }

    override val homeCommander: HomeCommander by lazy {
        HomeCommander(
            db = db,
            repo = homeRepo,
            executor = auditExecutor
        )
    }

    override val auditInfoRepo: AuditInfoRepo by lazy {
        AuditInfoRepo(
            db = db,
            ui = ui,
            executor = executor
        )
    }

}