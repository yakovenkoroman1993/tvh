package com.example.tvh.model

import androidx.compose.Model
import com.example.tvh.utils.newUUID
import java.util.*

@Model
class Group(
    val name: String
) {
    val id : UUID = newUUID()
}