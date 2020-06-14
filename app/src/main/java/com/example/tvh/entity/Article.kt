package com.example.tvh.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [
    Index(value = ["uid"], unique = true)
])
data class Article(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val desc: String,
    @ColumnInfo(name = "img_url") val imgUrl: String = "",
    @ColumnInfo(name = "source_url") val srcUrl: String = "",
    @ColumnInfo(name = "as_news") val asNews: Int = 0,
    @ColumnInfo(name = "created_at") val createdAt: String = Date().time.toString(),
    @ColumnInfo(name = "updated_at") val updatedAt: String = createdAt
)