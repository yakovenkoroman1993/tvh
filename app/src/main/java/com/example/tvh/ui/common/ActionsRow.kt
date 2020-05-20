package com.example.tvh.ui.common

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Row
import androidx.ui.layout.RowScope
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun ActionsRow(
    children: @Composable() RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Text(
            text = "Actions:",
//            modifier = Flexible(1f) wraps Gravity.Center,
            style = MaterialTheme.typography.subtitle1
        )
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