package com.example.tvh.ui.common

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.Gravity
import androidx.ui.layout.Row
import androidx.ui.layout.RowScope
import androidx.ui.layout.Spacing
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview

@Composable
fun ActionsRow(
    children: @Composable() RowScope.() -> Unit
) {
    Row(
        modifier = Spacing(left = 16.dp, right = 16.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Text(
            text = "Actions:",
            modifier = Flexible(1f) wraps Gravity.Center,
            style = (+MaterialTheme.typography()).subtitle1
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