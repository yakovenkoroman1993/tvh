package com.example.tvh.ui.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun ActionsRow(
    children: @Composable() RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Actions:",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(top = 8.dp)
            )
        }
        Row (children = children)
    }
}

@Preview
@Composable
fun PreviewActionsRow() {
    ActionsRow {
        Text("children")
    }
}