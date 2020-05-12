package com.example.tvh.di

import com.example.tvh.services.Commander
import com.example.tvh.services.Loader
import com.example.tvh.services.Navigator

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val loader: Loader
    val commander: Commander
    val navigator: Navigator
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl : AppContainer {
    override val navigator: Navigator by lazy {
        Navigator()
    }

    override val loader: Loader by lazy {
        Loader()
    }

    override val commander: Commander by lazy {
        Commander()
    }
}