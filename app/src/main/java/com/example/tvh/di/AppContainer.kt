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
interface IAppContainer {
    val navigator: INavigator
    val ui: UiModel
    val homeRepo: HomeRepo
    val homeCommander: HomeCommander
    val auditInfoRepo: AuditInfoRepo
    fun destroy()
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainer(private val applicationContext: Context) : IAppContainer {
    private val device by lazy {
        DeviceInfoProvider(applicationContext)
    }

    private val db by lazy {
        AppDatabase.create(applicationContext)
    }

    private val rdb by lazy {
        RemoteDatabase(applicationContext)
    }

    private val executor by lazy {
        AppExecutor(ui)
    }

    private val auditDocManager by lazy {
        AuditDocManager(
            db = db,
            rdb = rdb,
            executor = executor,
            device = device
        )
    }

    private val auditExecutor by lazy {
        AuditExecutor(
            auditDao = db.auditDao(),
            executor = executor,
            auditDocManager = auditDocManager
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

    override fun destroy() {
        auditDocManager.unsubscribe()
    }
}