package com.example.tvh.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

enum class AuditType {
    CREATE,
    DELETE
}

@Entity
data class Audit (
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,

    @ColumnInfo(name = "type") val type: String = AuditType.CREATE.toString(),
    @ColumnInfo(name = "entity_type") val entityType: String,
    @ColumnInfo(name = "entity_uid") val entityUid: Int,

    @ColumnInfo(name = "created_at") val createdAt: String = Date().time.toString(),
    @ColumnInfo(name = "updated_at") val updatedAt: String = createdAt,

    @ColumnInfo(name = "published") val published: Boolean = false
)
