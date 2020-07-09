package com.user.tvh.di

import android.content.Context
import com.user.tvh.model.UiModel
import com.user.tvh.repo.HomeRepo
import com.user.tvh.services.*

/**
 * Dependency Injection container at the application level.
 */
interface IAppContainer {
    val navigator: INavigator
    val imageLoader: ImageLoader
    val ui: UiModel
    val homeRepo: HomeRepo
    val notifier: Notifier
    val rdb: RemoteDatabase

    fun destroy()
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainer(private val applicationContext: Context) : IAppContainer {
    private val executor by lazy {
        AppExecutor(ui)
    }

    override val notifier by lazy {
        Notifier(
            context = applicationContext
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
            rdb = rdb,
            ui = ui
        )
    }

    override val rdb by lazy {
        RemoteDatabase(applicationContext)
    }

    override val imageLoader by lazy {
        ImageLoader(executor)
    }

    override fun destroy() {
    }
}