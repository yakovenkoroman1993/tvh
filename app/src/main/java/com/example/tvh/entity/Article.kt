package com.example.tvh.entity

import java.util.*

data class Article(
    val uid: String = UUID.randomUUID().toString(),
    val name: String = "",
    val desc: String = "",
    val imgUrl: String = "",
    val srcUrl: String = "",
    val asNews: Int = 0,
    val createdAt: String = Date().time.toString(),
    val updatedAt: String = createdAt
)