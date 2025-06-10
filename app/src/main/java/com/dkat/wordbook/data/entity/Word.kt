package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "word",
        foreignKeys = [
            ForeignKey(entity = Source::class,
                       parentColumns = ["_source_id"],
                       childColumns = ["source_id"],
                       onDelete = ForeignKey.CASCADE,
            ),
            ForeignKey(entity = Language::class,
                       parentColumns = ["_language_id"],
                       childColumns = ["lang_id"],
                       onDelete = ForeignKey.CASCADE,
            )
        ]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_word_id")   val id: Int = 0,
    @ColumnInfo(name = "source_id")  val sourceId: Int = 0,
    @ColumnInfo(name = "lang_id")    val languageId: Int = 0,
    @ColumnInfo(name = "word_value")      val value: String = "",
)
