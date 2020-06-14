package com.example.tvh.ui.common

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.Checkbox
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.example.tvh.services.ComponentsWithClipboardManager

@Composable
fun ArticleForm(
    nameTextFieldState: MutableState<TextFieldValue>,
    imgUrlTextFieldState: MutableState<TextFieldValue>,
    srcUrlTextFieldState: MutableState<TextFieldValue>,
    descTextFieldState: MutableState<TextFieldValue>,
    asNewsState: MutableState<Boolean>? = null,
    componentsWithClipboardManager: ComponentsWithClipboardManager
) {
    componentsWithClipboardManager.TextFieldWithHint(
        label = "Заголовок",
        value = nameTextFieldState.value,
        hint = "Пожалуйста, укажите заголовок",
        onChange = {
            nameTextFieldState.value = it
        }
    )
    Spacer(modifier = Modifier.preferredHeight(5.dp))
    componentsWithClipboardManager.TextFieldWithHint(
        label = "Источник",
        value = srcUrlTextFieldState.value,
        hint = "Пожалуйста, укажите ссылку на источник",
        keyboardType = KeyboardType.Uri,
        onChange = {
            srcUrlTextFieldState.value = it
        }
    )
    Spacer(modifier = Modifier.preferredHeight(5.dp))
    componentsWithClipboardManager.TextFieldWithHint(
        label = "Изображение",
        value = imgUrlTextFieldState.value,
        hint = "Пожалуйста, укажите ссылку на изображение",
        keyboardType = KeyboardType.Uri,
        onChange = {
            imgUrlTextFieldState.value = it
        }
    )
    Spacer(modifier = Modifier.preferredHeight(5.dp))
    componentsWithClipboardManager.TextFieldWithHint(
        label = "Описание",
        value = descTextFieldState.value,
        hint = "Пожалуйста, заполните описание",
        onChange = {
            descTextFieldState.value = it
        }
    )
    if (asNewsState == null) {
        return
    }

    Spacer(modifier = Modifier.preferredHeight(5.dp))
    Row {
        Checkbox(
            checked = asNewsState.value,
            color = MaterialTheme.colors.primaryVariant,
            onCheckedChange = {
                asNewsState.value = it
            }
        )
        Spacer(Modifier.preferredWidth(14.dp))
        Text(
            modifier = Modifier.padding(4.dp),
            text = "Как главная новость"
        )
    }
}