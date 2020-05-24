package com.example.tvh

import android.app.Application
import com.example.tvh.di.IAppContainer
import com.example.tvh.di.AppContainer

class TvhApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var containerI: IAppContainer

    override fun onCreate() {
        super.onCreate()
        this.containerI = AppContainer(applicationContext)
    }
}