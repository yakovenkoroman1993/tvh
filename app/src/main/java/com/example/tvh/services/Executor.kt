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

//interface Auditor {
//    fun audit()
//}
//class GroupAuditor : Auditor {
//    override fun audit() {
//
//    }
//}
//
//class AuditableExecutor(
//    private val ui: UiModel,
//    private val auditor: Auditor
//
//) : ThreadsafeExecutor() {
//    fun <T>run(command: () -> T, callback: (T) -> Unit) {
//        // Group create lastId func,
//        ui.isLoading = true
//        run(
//            command = command,
//            onSuccess = {
//                auditor.audit()
//                callback(it)
//            },
//            onFail = { throw it },
//            onFinal = { ui.isLoading = false }
//        )
//    }
//}
//
