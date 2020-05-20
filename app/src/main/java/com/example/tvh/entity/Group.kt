package com.example.tvh.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String

//    @ColumnInfo(name = "created_at") val createdAt: String = Date().toString(),
//    @ColumnInfo(name = "updated_at") val updatedAt: String = createdAt
)