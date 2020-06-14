package com.example.tvh.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

enum class AuditType {
    CREATE,
    DELETE
}

@Entity(indices = [
    Index(value = ["uid"], unique = true)
])
data class Audit (
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "type") val type: String = AuditType.CREATE.name,
    @ColumnInfo(name = "entity_type") val entityType: String,
    @ColumnInfo(name = "entity_uid") val entityUid: String,
    @ColumnInfo(name = "created_at") val createdAt: String = Date().time.toString(),
    @ColumnInfo(name = "updated_at") val updatedAt: String = createdAt,
    @ColumnInfo(name = "published") val published: Boolean = false
)
