package com.example.tvh.ui.home

import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.unit.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.ripple
import com.example.tvh.entity.Group
import com.example.tvh.services.Navigator
import com.example.tvh.utils.Utils
import java.util.*

@Composable
fun Home(
    groups: List<Group>,
    onAddGroup: (group: Group) -> Unit = {},
    onRemoveGroup: (group: Group) -> Unit = {},
    onNavigateToGroup: (screen: Navigator.Screen) -> Unit = {}
) {
    val rowModifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    Column {
        HomeHeader(
            actionsRowModifier = rowModifier,
            onAddGroup = onAddGroup
        )
        HomeBody {
            groups.forEach { group ->
                Clickable(
                    modifier = Modifier.ripple(),
                    onClick = {
                        onNavigateToGroup(
                            Navigator.Screen.GroupScreen(group)
                        )
                    }
                ) {
                    GroupItem(
                        modifier = rowModifier,
                        group = group,
                        onCopy = {
                            onAddGroup(
                                group.copy(
                                    createdAt = Date().time.toString(),
                                    updatedAt = Date().time.toString()
                                )
                            )
                        },
                        onRemove = { onRemoveGroup(group) }
                    )
                }
            }
        }
    }
}


@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    group: Group,
    onCopy: () -> Unit,
    onRemove: () -> Unit
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            ProvideEmphasis(EmphasisAmbient.current.high) {
                Text(group.name, style = MaterialTheme.typography.subtitle1)
            }
            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Row {
                    Text(
                        text = "Created",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = " - ${Utils.DateTime.getPrettyTime(group.createdAt)}",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
        Row(
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Button(onClick = onCopy) {
                Text("Copy")
            }
            Spacer(Modifier.preferredWidth(4.dp))
            TextButton(onClick = onRemove) {
                Text("Remove")
            }
        }
    }
}
