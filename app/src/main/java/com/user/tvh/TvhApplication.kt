package com.user.tvh

import android.app.Application
import com.user.tvh.di.IAppContainer
import com.user.tvh.di.AppContainer

class TvhApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var appContainer: IAppContainer

    override fun onCreate() {
        super.onCreate()
        this.appContainer = AppContainer(applicationContext)
    }
}