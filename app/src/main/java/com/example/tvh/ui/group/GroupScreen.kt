package com.example.tvh.ui.group

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.tvh.di.AppContainer
import com.example.tvh.services.Navigator

@Composable
fun GroupScreen(
    text: String,
    title: String,
    appContainer: AppContainer
) {
    val navigator = appContainer.navigator

    Group(
        title = title,
        body = text,
        style = MaterialTheme.typography.subtitle1,
        onNavigateTo = {
            navigator.navigateTo(it)
        }
    )
}

@Composable
fun Group(
    title: String,
    body: String,
    style: TextStyle,
    onNavigateTo: (Navigator.Screen) -> Unit = {}
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Details for $title",
                    modifier = Modifier.padding(12.dp),
                    style = style
                )
            },
            navigationIcon = {
                IconButton(onClick = { onNavigateTo(Navigator.Screen.HomeScreen) }) {
                    Icon(Icons.Filled.ArrowBack)
                }
            }
        )

        Text(
            text = body,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Preview
@Composable
fun PreviewArticle() {
    Group(
        title = "title",
        body = "body",
        style = TextStyle()
    )
}
