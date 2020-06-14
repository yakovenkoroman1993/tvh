package com.example.tvh.ui.common

import android.content.ClipboardManager
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.foundation.TextFieldValue
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.TextButton
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun TextFieldWithHint(
    value: TextFieldValue,
    label: String = "",
    hint: String = "Please, fill field",
    keyboardType: KeyboardType = KeyboardType.Text,
    clipboard: ClipboardManager? = null,
    onChange: (TextFieldValue) -> Unit
) {
    if (label.isEmpty()) {
        TextFieldOnlyWithHint(
            value = value,
            hint = hint,
            keyboardType = keyboardType,
            clipboard = clipboard,
            onChange = onChange
        )
    }
    else {
        Column {
            Text(label)
            Spacer(Modifier.preferredHeight(4.dp))
            TextFieldOnlyWithHint(
                value = value,
                hint = hint,
                keyboardType = keyboardType,
                clipboard = clipboard,
                onChange = onChange
            )
        }
    }
}

@Composable
fun TextFieldOnlyWithHint(
    value: TextFieldValue,
    hint: String = "Please, fill field",
    keyboardType: KeyboardType = KeyboardType.Text,
    clipboard: ClipboardManager? = null,
    onChange: (TextFieldValue) -> Unit
) {
    Column {
        Row {
            Surface(
                modifier = Modifier.fillMaxWidth().weight(4f),
                shape = RoundedCornerShape(3.dp),
                color = Color.LightGray
            ) {
                Row {
                    TextField(
                        modifier = Modifier.padding(
                            top = 12.dp,
                            bottom = 12.dp,
                            start = 4.dp,
                            end = 4.dp
                        ),
                        value = value,
                        textStyle = TextStyle(fontSize = MaterialTheme.typography.h5.fontSize),
                        onValueChange = onChange,
                        keyboardType = keyboardType
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        if (value.text.isEmpty()) {
            Text(
                text = hint,
                style = TextStyle(
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray,
                    fontSize = MaterialTheme.typography.subtitle2 .fontSize
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        if (clipboard != null) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = {
                        onChange(TextFieldValue(
                            clipboard.primaryClip?.getItemAt(0)?.text?.toString() ?: ""
                        ))
                    }
                ) {
                    Text("Вставить", style = TextStyle(fontSize = MaterialTheme.typography.caption.fontSize))
                }
                TextButton(
                    onClick = { onChange(TextFieldValue("")) }
                ) {
                    Text("Очистить", style = TextStyle(fontSize = MaterialTheme.typography.caption.fontSize))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTextFieldWithHint() {
    TextFieldWithHint(
        label = "Project Name:",
        value = TextFieldValue(""),
        hint = "hint",
        onChange = {}
    )
}