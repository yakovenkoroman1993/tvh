package com.example.tvh.services

import com.example.tvh.model.UiModel

class Executor(private val ui: UiModel) : ThreadsafeExecutor() {
    fun <T>run(command: () -> T, callback: (T) -> Unit) {
        ui.isLoading = true
        run(
            command = command,
            onSuccess = callback,
            onFail = { throw it },
            onFinal = { ui.isLoading = false }
        )
    }
}
