package com.example.tvh.dao

import androidx.room.*
import com.example.tvh.entity.Audit

@Dao
interface AuditDao {

    @Query("SELECT * FROM `audit` ORDER BY created_at DESC")
    fun findAll(): List<Audit>

    @Query("SELECT * FROM `audit` WHERE uid = :uid")
    fun find(uid: String): Audit

    @Insert
    fun create(audit: Audit)

    @Update
    fun update(audit: Audit): Int

    @Delete
    fun delete(audit: Audit): Int
}