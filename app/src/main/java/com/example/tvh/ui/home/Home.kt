package com.example.tvh.ui.home

import androidx.compose.*
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.ContainedButtonStyle
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButtonStyle
import com.example.tvh.entity.Group
import com.example.tvh.services.Navigator

@Composable
fun Home(
    groups: List<Group>,
    onAddGroup: (group: Group) -> Unit = {},
    onRemoveGroup: (group: Group) -> Unit = {},
    onNavigateToGroup: (screen: Navigator.Screen) -> Unit = {}
) {

    Column {
        HomeHeader(
            onAddGroup = onAddGroup
        )
        HomeBody {
            groups.forEach { group ->
                Clickable(
                    onClick = {
                        onNavigateToGroup(
                            Navigator.Screen.GroupScreen(group)
                        )
                    }
                ) {
                    GroupItem(
                        text = group.name,
                        onCopy = { onAddGroup(group) },
                        onRemove = { onRemoveGroup(group) }
                    )
                }
            }
        }
    }
}


@Composable
fun GroupItem(
    text: String,
    onCopy: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Spacing(left = 16.dp, right = 16.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Text(
            text = text,
            modifier = Flexible(1f) wraps Gravity.Center,
            style = (+MaterialTheme.typography()).subtitle1
        )

        Row {
            Button(
                "Copy",
                style = ContainedButtonStyle(),
                onClick = onCopy
            )
            Button(
                "Remove",
                style = TextButtonStyle(),
                onClick = onRemove
            )
        }
    }
}
