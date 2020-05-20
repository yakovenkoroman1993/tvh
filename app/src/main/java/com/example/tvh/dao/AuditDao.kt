package com.example.tvh.dao

import androidx.room.*
import com.example.tvh.entity.Audit

@Dao
interface AuditDao {

    @Query("SELECT * FROM `audit`")
    fun findAll(): List<Audit>

    @Query("SELECT * FROM `audit` WHERE uid = :uid")
    fun find(uid: Int): Audit

    @Insert
    fun create(vararg audit: Audit)

    @Update
    fun update(audit: Audit)

    @Delete
    fun delete(audit: Audit)
}