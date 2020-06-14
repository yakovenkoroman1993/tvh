package com.example.tvh.di

import android.content.Context
import com.example.tvh.model.UiModel
import com.example.tvh.repo.HomeRepo
import com.example.tvh.services.*

/**
 * Dependency Injection container at the application level.
 */
interface IAppContainer {
    val navigator: INavigator
    val imageLoader: ImageLoader
    val ui: UiModel
    val homeRepo: HomeRepo

    fun destroy()
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainer(private val applicationContext: Context) : IAppContainer {
    private val rdb by lazy {
        RemoteDatabase(applicationContext)
    }

    private val executor by lazy {
        AppExecutor(ui)
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

    override val imageLoader by lazy {
        ImageLoader(executor)
    }

    override fun destroy() {
    }
}