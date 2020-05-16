package com.example.tvh.model

import androidx.compose.Model

@Model
class UiModel {
    var isLoading: Boolean = false
    var home: Home = Home()

}
