package com.example.tvh.services

import com.example.tvh.model.Group
import com.example.tvh.model.Home
import java.util.*

class Commander(private val stateManagement: StateManagement) {
    fun addHomeGroup(name: String) {
        stateManagement.home = Home(
            groups = stateManagement.home.groups.plus(Group(name))
        )
    }

    fun removeHomeGroup(id: UUID) {
        stateManagement.home = Home(
            groups = stateManagement.home.groups.filter { it.id != id }
        )
    }
}
