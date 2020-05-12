package com.example.tvh

import android.app.Application
import com.example.tvh.di.AppContainer
import com.example.tvh.di.AppContainerImpl

class TodoListApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        this.container = AppContainerImpl()
    }
}