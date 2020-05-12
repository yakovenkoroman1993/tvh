package com.example.tvh.services

import com.example.tvh.model.Group
import com.example.tvh.model.Home

class StateManagement {
    var home: Home = Home(
        groups = listOf(
            Group(name = "group 1"),
            Group(name = "group 2"),
            Group(name = "group 3")
        )
    )
}