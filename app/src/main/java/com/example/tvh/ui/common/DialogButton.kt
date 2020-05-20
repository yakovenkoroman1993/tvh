package com.example.tvh.ui.common

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.unit.dp
import com.example.tvh.ui.lightThemeColors
import com.example.tvh.ui.themeTypography

@Composable
fun DialogButton(
    buttonText: String,
    dialogTitle: String = buttonText,
    disabledPrimaryButton: Boolean = false,
    onApply: () -> Unit = {},
    children: @Composable() () -> Unit
) {
    val (dialogVisible, setDialogVisible) = state { false }
    Button(onClick = { setDialogVisible(true) }) {
        Text(buttonText)
    }

    if (!dialogVisible) {
        return
    }

    DialogForm(
        dialogTitle = dialogTitle,
        primaryButtonText = buttonText,
        disabledPrimaryButton = disabledPrimaryButton,
        onClose = { setDialogVisible(false) },
        onApply = {
            onApply()
            setDialogVisible(false)
        }
    ) {
        children()
    }
}

@Composable
fun DialogForm(
    dialogTitle: String,
    disabledPrimaryButton: Boolean,
    primaryButtonText: String,
    onApply: () -> Unit,
    onClose: () -> Unit,
    children: @Composable() () -> Unit
) {
    Dialog(onCloseRequest = onClose) {
        MaterialTheme(
            colors = lightThemeColors,
            typography = themeTypography
        ) {
            Surface(
                shape = RoundedCornerShape(0),
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Box(modifier = Modifier.weight(1f)) {
                            Column {
                                DialogTitle(dialogTitle)
                                Spacer(Modifier.preferredHeight(16.dp))
                                children()
                            }
                        }
                        DialogActions(
                            disabled = disabledPrimaryButton,
                            primaryText = primaryButtonText,
                            onApply = onApply,
                            onClose = onClose
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DialogTitle(titleText: String) {
    Text(
        text = titleText,
        style = MaterialTheme.typography.h6
    )
}

@Composable
fun DialogActions(
    disabled: Boolean,
    primaryText: String = "Apply",
    onApply: () -> Unit,
    onClose: () -> Unit
) {
    Row {
        Box(modifier = Modifier.weight(1f)) {
            if (disabled) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.LightGray,
                    onClick = onApply
                ) {
                    Text(primaryText)
                }
            }
            else {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onApply
                ) {
                    Text(primaryText)
                }
            }
        }
        Spacer(Modifier.preferredWidth(8.dp))
        Box(modifier = Modifier.weight(1f)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.Transparent,
                onClick = onClose
            ) {
                Text("Dismiss")
            }
        }
    }
}
