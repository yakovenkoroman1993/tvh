package com.example.tvh.dao

import androidx.room.*
import com.example.tvh.entity.Group

@Dao
interface GroupDao {

    @Query("SELECT * FROM `group`")
    fun findAll(): List<Group>

    @Query("SELECT * FROM `group` WHERE uid = :uid")
    fun find(uid: Int): Group

    @Insert
    fun create(vararg groups: Group): List<Long>

    @Update
    fun update(group: Group): Int

    @Delete
    fun delete(group: Group): Int
}