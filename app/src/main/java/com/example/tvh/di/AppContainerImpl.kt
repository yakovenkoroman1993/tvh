package com.example.tvh.di

import android.content.Context
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

    private val device by lazy {
        DeviceInfo(applicationContext)
    }

    private val db by lazy {
        AppDatabase.create(applicationContext)
    }

    private val dbRemote by lazy {
        RemoteDatabase(applicationContext)
    }

    private val executor by lazy {
        Executor(ui)
    }

    private val auditExecutor by lazy {
        AuditExecutor(
            db = db,
            dbRemote = dbRemote,
            executor = executor,
            device = device
        )
    }

    override val navigator by lazy {
        Navigator()
    }

    override val ui by lazy {
        UiModel()
    }

    override val homeRepo by lazy {
        HomeRepo(
            db = db,
            ui = ui,
            executor = executor
        )
    }

    override val homeCommander by lazy {
        HomeCommander(
            db = db,
            repo = homeRepo,
            executor = auditExecutor
        )
    }

    override val auditInfoRepo by lazy {
        AuditInfoRepo(
            db = db,
            ui = ui,
            executor = executor
        )
    }

}