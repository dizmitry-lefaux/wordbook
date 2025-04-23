package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translation")
data class Translation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")      val id: Int = 0,
    @ColumnInfo(name = "word_id") val wordId: Int = 0,
    @ColumnInfo(name = "value")   val value: String = "",
    @ColumnInfo(name = "lang_id") val languageId: Int = 0
)
