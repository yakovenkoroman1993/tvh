package com.example.tvh.di

import android.content.Context
import com.example.tvh.commander.ArticleCommander
import com.example.tvh.model.UiModel
import com.example.tvh.repo.AuditInfoRepo
import com.example.tvh.repo.HomeRepo
import com.example.tvh.services.*

/**
 * Dependency Injection container at the application level.
 */
interface IAppContainer {
    val navigator: INavigator
    val imageLoader: ImageLoader
    val ui: UiModel
    val auditDocManager: IAuditDocManager
    val homeRepo: HomeRepo
    val articleCommander: ArticleCommander
    val auditInfoRepo: AuditInfoRepo
    val componentsWithClipboardManager: ComponentsWithClipboardManager

    fun destroy()
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainer(private val applicationContext: Context) : IAppContainer {
    private val device by lazy {
        DeviceInfoProvider(context = applicationContext)
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

    override val componentsWithClipboardManager by lazy {
        ComponentsWithClipboardManager(context = applicationContext)
    }

    override val auditDocManager by lazy {
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
        Navigator(context = applicationContext)
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

    override val articleCommander by lazy {
        ArticleCommander(
            db = db,
            repo = homeRepo,
            executor = executor,
            auditExecutor = auditExecutor,
            rdb = rdb
        )
    }

    override val auditInfoRepo by lazy {
        AuditInfoRepo(
            db = db,
            ui = ui,
            executor = executor
        )
    }

    override val imageLoader by lazy {
        ImageLoader(executor)
    }

    override fun destroy() {
        auditDocManager.destroy()
    }
}