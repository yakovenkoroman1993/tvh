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
                    modifier = Modifier.ripple(),
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
    Row(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            ProvideEmphasis(EmphasisAmbient.current.high) {
                Text(text, style = MaterialTheme.typography.subtitle1)
            }
            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Row {
                    Text(text = "Creator", style = MaterialTheme.typography.body2)
                    Text(text = " - ${1} min read", style = MaterialTheme.typography.body2)
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
