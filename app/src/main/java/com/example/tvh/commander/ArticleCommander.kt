package com.example.tvh.commander

import com.example.tvh.entity.Article
import com.example.tvh.repo.HomeRepo
import com.example.tvh.services.*
import java.util.*

class ArticleCommander(
    private val db: AppDatabase,
    private val repo: HomeRepo,
    private val executor: AppExecutor,
    private val auditExecutor: AuditExecutor,
    private val rdb: IRemoteDatabase
) {
    fun updateNewsArticle(article: Article) {
        executor.run({
            db.articleDao().findNews()
        }) { articleNews ->
            if (articleNews == null) {
                add(article)
            } else {
                update(article.copy(uid = articleNews.uid))
            }
        }
    }

    fun update(article: Article) {
        auditExecutor.runToUpdate(Article::class, {
            db.articleDao().update(article)
            article.uid
        }) {
            repo.loadHome()

            rdb.push(
                collectionName = RemoteDatabase.Collections.Article.name,
                docUid = article.uid,
                entity = article
            )
        }
    }

    fun add(article: Article) {
        auditExecutor.runToCreate(Article::class, {
            db.articleDao().create(article)
            article.uid
        }) {
            repo.loadHome()

            rdb.push(
                collectionName = RemoteDatabase.Collections.Article.name,
                docUid = article.uid,
                entity = article
            )
        }
    }

    fun remove(article: Article) {
        auditExecutor.runToDelete(Article::class, {
            db.articleDao().delete(article)
            article.uid
        }) {
            repo.loadHome()

            rdb.push(
                collectionName = RemoteDatabase.Collections.Article.name,
                docUid = article.uid,
                entity = null
            )
        }
    }
}