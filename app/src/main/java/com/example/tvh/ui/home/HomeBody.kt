package com.example.tvh.ui.home

import androidx.compose.Composable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column

@Composable
fun HomeBody(
    children: @Composable() () -> Unit
) {
    VerticalScroller {
        Column {
            children()
        }
    }
}
