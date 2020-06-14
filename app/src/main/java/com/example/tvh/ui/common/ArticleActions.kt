package com.example.tvh.ui.common

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.foundation.TextFieldValue
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import com.example.tvh.entity.Article
import com.example.tvh.services.ComponentsWithClipboardManager

@Composable
fun ArticleActions(
    componentsWithClipboardManager: ComponentsWithClipboardManager,
    onAddArticle: (article: Article) -> Unit
) {
    val nameTextFieldState = state { TextFieldValue() }
    val (nameTextField, setNameTextField) = nameTextFieldState
    val imgUrlTextFieldState = state { TextFieldValue() }
    val (imgUrlTextField, setImgUrlTextField) = imgUrlTextFieldState
    val srcUrlTextFieldState = state { TextFieldValue() }
    val (srcUrlTextField, setSrcUrlTextField) = srcUrlTextFieldState
    val descTextFieldState = state { TextFieldValue() }
    val (descTextField, setDescTextField) = descTextFieldState
    val asNewsState = state { true }
    val (asNews) = asNewsState

    DialogButton(
        buttonText = "Добавить",
        dialogTitle = "Добавить информацию",
        disabledPrimaryButton = nameTextField.text.isEmpty(),
        onApply = {
            onAddArticle(
                Article(
                    name = nameTextField.text,
                    imgUrl = imgUrlTextField.text,
                    srcUrl = srcUrlTextField.text,
                    desc = descTextField.text,
                    asNews = if (asNews) 1 else 0
                )
            )
            setNameTextField(TextFieldValue(text = ""))
            setImgUrlTextField(TextFieldValue(text = ""))
            setSrcUrlTextField(TextFieldValue(text = ""))
            setDescTextField(TextFieldValue(text = ""))
        }
    ) {
        VerticalScroller {
            Column {
                ArticleForm(
                    nameTextFieldState = nameTextFieldState,
                    imgUrlTextFieldState = imgUrlTextFieldState,
                    srcUrlTextFieldState = srcUrlTextFieldState,
                    descTextFieldState = descTextFieldState,
                    asNewsState = asNewsState,
                    componentsWithClipboardManager = componentsWithClipboardManager
                )
            }
        }
    }
}
