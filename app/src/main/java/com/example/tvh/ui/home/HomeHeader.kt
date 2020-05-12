package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.EditorModel
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.core.dp
import androidx.ui.layout.*
import com.example.tvh.ui.common.ActionsRow
import com.example.tvh.ui.common.DialogButton

@Composable
fun HomeHeader(
    onAddProject: (projectName: String) -> Unit
) {
    val editorModel = +state { EditorModel() }

    Column {
        ActionsRow {
            DialogButton(
                text = "New",
                onOk = {
                    onAddProject(editorModel.value.text)
                    editorModel.value = EditorModel("")
                }
            ) {
                Padding(padding = 24.dp) {
                    Column {
                        Text("Name:")
                        TextField(
                            value = editorModel.value,
                            onValueChange = {
                                editorModel.value = it
                            }
                        )
                    }
                }
            }
        }
    }
}
