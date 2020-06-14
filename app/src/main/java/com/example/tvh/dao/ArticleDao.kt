package com.example.tvh.dao

import androidx.room.*
import com.example.tvh.entity.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article")
    fun findAll(): List<Article>

    @Query("SELECT * FROM Article WHERE uid = :uid")
    fun find(uid: String): Article?

    @Query("SELECT * FROM Article WHERE as_news = 1 ORDER BY created_at DESC LIMIT 1")
    fun findNews(): Article?

    @Insert
    fun create(article: Article)

    @Update
    fun update(article: Article): Int

    @Delete
    fun delete(article: Article): Int
}