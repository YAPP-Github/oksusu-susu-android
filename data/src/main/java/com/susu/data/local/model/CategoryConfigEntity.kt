package com.susu.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.susu.core.model.Category

@Entity(tableName = EntityTable.CATEGORY_CONFIG)
data class CategoryConfigEntity(
    @PrimaryKey
    val id: Int,
    val seq: Int,
    val name: String,
    val style: String,
)

internal fun CategoryConfigEntity.toModel() = Category(
    id = id,
    seq = seq,
    name = name,
    style = style,
)

internal fun Category.toEntity() = CategoryConfigEntity(
    id = id,
    seq = seq,
    name = name,
    style = style,
)
