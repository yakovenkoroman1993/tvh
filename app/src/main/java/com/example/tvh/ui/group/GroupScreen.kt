package com.example.tvh.ui.group

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.sp
import androidx.ui.layout.Column
import androidx.ui.layout.Padding
import androidx.ui.material.*
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import com.example.tvh.di.AppContainer
import com.example.tvh.services.Navigator

@Composable
fun GroupScreen(
    text: String,
    title: String,
    appContainer: AppContainer
) {
    val navigator = appContainer.navigator

    val style = +MaterialTheme.typography()
    Group(
        title = title,
        body = text,
        style = style.subtitle1 ,
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
                Padding(padding = 12.dp) {
                    Text(
                        text = "Details for $title",
                        style = style
                    )
                }
            },
            navigationIcon = {
                Button(
                    text = "<",
                    style = ContainedButtonStyle().copy(
                        textStyle = TextStyle(fontSize = 24.sp)
                    ),
                    onClick = {
                        onNavigateTo(Navigator.Screen.HomeScreen)
                    }
                )
            }
        )
        Padding(padding = 12.dp) {
            Text(body)
        }
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
