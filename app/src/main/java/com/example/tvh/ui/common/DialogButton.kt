package com.example.tvh.ui.common

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun DialogButton(
    visible: Boolean = false,
    text: String,
    onOk: () -> Unit = {},
    children: @Composable() () -> Unit
) {
    val (visible, setVisible) = state { visible }

    Button(onClick = { setVisible(true) }) {
        Text(text)
    }
    if (visible) {
        DialogForm(
            titleText = text,
            onClose = { setVisible(false) },
            onOk = {
                onOk()
                setVisible(false)
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
            Box {
                Column {
                    DialogTitle(titleText)

                    children()

                    Spacer(Modifier.preferredHeight(28.dp))

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
    Box(
//        modifier = Modifier.gravity(Alignment.CenterStart),
        modifier = Modifier.padding(24.dp)
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun DialogContent(
    value: TextFieldValue,
    onChange: (value: TextFieldValue) -> Unit
) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text("Name:")
        TextField(
            value = value,
            onValueChange = onChange
        )
    }

}

@Composable
fun DialogActions(
    onOk: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        padding = 8.dp
//        modifier = Modifier.gravity(Alignment.CenterEnd),
    ) {
        Row {
            Button(onClick = onOk) {
                Text("Ok")
            }
            Spacer(Modifier.preferredWidth(8.dp))
            Button(onClick = onClose) {
                Text("Dismiss")
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialogContent() {
    DialogContent(
        TextFieldValue("123"),
        onChange = {}
    )
}
