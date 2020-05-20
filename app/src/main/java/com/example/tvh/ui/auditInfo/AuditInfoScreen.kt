package com.example.tvh.ui.auditInfo

import androidx.compose.Composable
import androidx.compose.onActive
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.example.tvh.di.AppContainer
import com.example.tvh.entity.Audit

@Composable
fun AuditInfoScreen(appContainer: AppContainer) {
    val repo = appContainer.auditInfoRepo
    val ui = appContainer.ui

    onActive {
        repo.loadAudit()
    }

    AuditInfo(logs = ui.auditInfo.logs)
}

@Composable
fun AuditInfo(logs: List<Audit>) {
    VerticalScroller {
        Column {
            if (logs.isEmpty()) {
                Row(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
                ) {
                    Text(
                        text = "Logs not found",
                        style = MaterialTheme.typography.subtitle1
                    )
                }

                return@Column
            }

            logs.forEach {
                AuditLogItem(it)
            }
        }
    }
}

@Composable
fun AuditLogItem(log: Audit) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Column {
            Text(
                text = log.createdAt,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "${log.entityType}  with uid = ${log.entityUid} was ${log.type}D",
                style = MaterialTheme.typography.subtitle2
            )
        }

    }
}
