package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "word",
        foreignKeys = [
            ForeignKey(entity = Source::class,
                       parentColumns = ["id"],
                       childColumns = ["source_id"],
                       onDelete = ForeignKey.CASCADE,
            ),
            ForeignKey(entity = Language::class,
                       parentColumns = ["id"],
                       childColumns = ["lang_id"],
                       onDelete = ForeignKey.CASCADE,
            )
        ]
)
data class Word_B(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "source_id")  val sourceId: Int = 0,
    @ColumnInfo(name = "lang_id")    val languageId: Int = 0,
    @ColumnInfo(name = "value")      val value: String = "",
)
