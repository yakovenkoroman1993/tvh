package com.example.tvh.ui.auditInfo

import androidx.compose.Composable
import androidx.compose.onActive
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.unit.dp
import com.example.tvh.di.IAppContainer
import com.example.tvh.entity.Audit
import com.example.tvh.entity.AuditType
import com.example.tvh.utils.Utils

@Composable
fun AuditInfoScreen(appContainer: IAppContainer) {
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
    val backGroundColor = when (log.type) {
        AuditType.CREATE.toString() -> Color.Green
        AuditType.DELETE.toString() -> Color.Red
        else -> throw Error("Unknown audit type")
    }
    Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)) {
        Surface(
            color = backGroundColor,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "${log.entityType}  with uid = ${log.entityUid}",
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = "Published: ${ if (log.published) "Yes" else "No" }",
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = "${log.type}D ${Utils.DateTime.getPrettyTime(log.createdAt)}",
                    style = MaterialTheme.typography.body2
                )
            }

        }
    }
}
