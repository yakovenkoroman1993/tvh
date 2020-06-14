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
    modifier: Modifier,
    children: @Composable() RowScope.() -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Действия:",
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
    ActionsRow(modifier = Modifier) {
        Text("children")
    }
}