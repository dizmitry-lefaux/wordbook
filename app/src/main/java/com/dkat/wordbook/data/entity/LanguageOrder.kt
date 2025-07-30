package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "language_order",
        indices = [Index(value = ["language_id"], unique = true)],
        foreignKeys = [
            ForeignKey(entity = Language::class,
                       parentColumns = ["_language_id"],
                       childColumns = ["language_id"],
                       onDelete = ForeignKey.CASCADE
            ),
        ]
)
data class LanguageOrder(
    @PrimaryKey
    @ColumnInfo(name = "language_id")    val languageId: Int,
    @ColumnInfo(name = "language_order") val order: Int
)
