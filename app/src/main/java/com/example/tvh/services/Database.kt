package com.example.tvh.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tvh.dao.GroupDao
import com.example.tvh.entity.Group

@Database(entities = [Group::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun groupDao(): GroupDao
}