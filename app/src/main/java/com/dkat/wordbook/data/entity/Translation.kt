package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "translation",
        foreignKeys = [
            ForeignKey(entity = Word::class,
                       parentColumns = ["_word_id"],
                       childColumns = ["word_id"],
                       onDelete = ForeignKey.CASCADE,
            ),
            ForeignKey(entity = Language::class,
                       parentColumns = ["_language_id"],
                       childColumns = ["lang_id"],
                       onDelete = ForeignKey.CASCADE,
            )
        ])
data class Translation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_translation_id")   val id: Int = 0,
    @ColumnInfo(name = "word_id")           val wordId: Int = 0,
    @ColumnInfo(name = "translation_value") val value: String = "",
    @ColumnInfo(name = "lang_id")           val languageId: Int = 0
)
