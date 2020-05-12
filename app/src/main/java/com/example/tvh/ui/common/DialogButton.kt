package com.example.tvh.ui.common

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.foundation.Dialog
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Surface
import androidx.ui.tooling.preview.Preview

@Composable
fun DialogButton(
    visible: Boolean = false,
    text: String,
    onOk: () -> Unit = {},
    children: @Composable() () -> Unit
) {
    val visibleModel = +state { visible }

    Button(
        text,
        style = ContainedButtonStyle(),
        onClick = {
            visibleModel.value = true
        }
    )
    if (visibleModel.value) {
        DialogForm(
            titleText = text,
            onClose = {
                visibleModel.value = false
            },
            onOk = {
                onOk()
                visibleModel.value = false
            }
        ) {
            children()
        }
    }
}

@Composable
fun DialogForm(
    titleText: String,
    onOk: () -> Unit,
    onClose: () -> Unit,
    children: @Composable() () -> Unit
) {
    Dialog(onCloseRequest = onClose) {
        Surface(shape = RoundedCornerShape(4.dp)) {
            Container(/*width = AlertDialogWidth*/) {
                Column {
                    DialogTitle(titleText)

                    children()

                    HeightSpacer(height = 28.dp)

                    DialogActions(
                        onOk = onOk,
                        onClose = onClose
                    )
                }
            }
        }
    }
}

@Composable
fun DialogTitle(titleText: String) {
    Container(
        alignment = Alignment.CenterLeft,
        padding = EdgeInsets(
            left = 24.dp, top = 24.dp, right = 24.dp, bottom = 0.dp
        )
    ) {
        val textStyle = (+MaterialTheme.typography()).h6
        CurrentTextStyleProvider(textStyle) {
            Text(titleText)
        }
    }
}

@Composable
fun DialogContent(
    value: EditorModel,
    onChange: (value: EditorModel) -> Unit
) {
    Padding(padding = 24.dp) {
        Column {
            Text("Name:")
            TextField(
                value = value,
                onValueChange = onChange
            )
        }
    }
}

@Composable
fun DialogActions(
    onOk: () -> Unit,
    onClose: () -> Unit
) {
    Container(
        ExpandedWidth,
        padding = EdgeInsets(all = 8.dp),
        alignment = Alignment.CenterRight
    ) {
        Row {
            Button("Ok", onClick = onOk)
            WidthSpacer(8.dp)
            Button("Dismiss", onClick = onClose)
        }
    }
}

@Preview
@Composable
fun PreviewDialogContent() {
    DialogContent(
        EditorModel("123"),
        onChange = {}
    )
}
