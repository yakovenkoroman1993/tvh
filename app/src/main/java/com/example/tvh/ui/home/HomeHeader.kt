package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.foundation.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.unit.dp
import com.example.tvh.entity.Group
import com.example.tvh.ui.common.ActionsRow
import com.example.tvh.ui.common.DialogButton

@Composable
fun HomeHeader(
    onAddGroup: (group: Group) -> Unit
) {
    val (textFieldValue, setTextFieldValue) = state { TextFieldValue() }

    Column {
        ActionsRow {
            DialogButton(
                text = "New",
                onOk = {
                    onAddGroup(
                        Group(name = textFieldValue.text)
                    )
                    setTextFieldValue(TextFieldValue(text = ""))
                }
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Name:")
                    TextField(
                        value = textFieldValue,
                        onValueChange = setTextFieldValue
                    )
                }
            }
        }
    }
}
