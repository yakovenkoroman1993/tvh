package com.example.tvh.services

import com.example.tvh.model.Home

class Loader(private val stateManagement: StateManagement) {
    fun getHome(): Home {
        return stateManagement.home
    }
}
