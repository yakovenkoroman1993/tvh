package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.TextFieldValue
import androidx.ui.layout.*
import com.example.tvh.entity.Group
import com.example.tvh.ui.common.ActionsRow
import com.example.tvh.ui.common.DialogButton
import com.example.tvh.ui.common.TextFieldWithHint

@Composable
fun HomeHeader(
    actionsRowModifier: Modifier = Modifier,
    onAddGroup: (group: Group) -> Unit
) {
    val (textFieldValue, setTextFieldValue) = state { TextFieldValue() }

    Column {
        ActionsRow(modifier = actionsRowModifier) {
            DialogButton(
                buttonText = "Create",
                dialogTitle = "New Group",
                disabledPrimaryButton = textFieldValue.text.isEmpty(),
                onApply = {
                    onAddGroup(Group(name = textFieldValue.text))
                    setTextFieldValue(TextFieldValue(text = ""))
                }
            ) {
                TextFieldWithHint(
                    label = "Name",
                    value = textFieldValue,
                    hint = "Please, type name",
                    onChange = setTextFieldValue
                )
            }
        }
    }
}
