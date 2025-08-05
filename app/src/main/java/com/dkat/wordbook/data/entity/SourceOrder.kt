package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "source_order",
        indices = [Index(value = ["source_id"], unique = true)],
        foreignKeys = [
            ForeignKey(entity = Source::class,
                       parentColumns = ["_source_id"],
                       childColumns = ["source_id"],
                       onDelete = ForeignKey.CASCADE
            ),
        ]
)
data class SourceOrder(
    @PrimaryKey
    @ColumnInfo(name = "source_id")    val sourceId: Int,
    @ColumnInfo(name = "source_order") val order: Int
)
