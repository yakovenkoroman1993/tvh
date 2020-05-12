package com.example.tvh.ui.home

import androidx.compose.*
import androidx.ui.foundation.Clickable
import androidx.ui.layout.*
import com.example.tvh.services.Navigator
import com.google.protobuf.ByteString

@Composable
fun Home(
    onAddProject: (name: String) -> Unit = {},
    onRemoveProject: (id: ByteString) -> Unit = {},
    onNavigateTo: (screen: Navigator.Screen) -> Unit = {}
) {

    Column {
        HomeHeader(
            onAddProject = onAddProject
        )
        HomeBody {
//            projects.forEach {
//                Clickable(
//                    onClick = {
//                        onNavigateTo(
//                            Navigator.Screen.Article(it)
//                        )
//                    }
//                ) {
//                    Article(
//                        text = it.name,
//                        onCopy = { onAddProject(it.name) },
//                        onRemove = { onRemoveProject(it.id) }
//                    )
//                }
//            }
        }
    }
}
