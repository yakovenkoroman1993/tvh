package com.example.tvh.services

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.Composable
import androidx.core.content.ContextCompat.getSystemService
import androidx.ui.foundation.TextFieldValue
import androidx.ui.input.KeyboardType

class ComponentsWithClipboardManager(context: Context) {
    private val clipboard: ClipboardManager = getSystemService(context, ClipboardManager::class.java) as ClipboardManager

    @Composable
    fun TextFieldWithHint(
        label: String,
        value: TextFieldValue,
        hint: String,
        keyboardType: KeyboardType = KeyboardType.Text,
        onChange: (TextFieldValue) -> Unit
    ) {
        return com.example.tvh.ui.common.TextFieldWithHint(
            label = label,
            value = value,
            hint = hint,
            clipboard = clipboard,
            keyboardType = keyboardType,
            onChange = onChange
        )
    }
}