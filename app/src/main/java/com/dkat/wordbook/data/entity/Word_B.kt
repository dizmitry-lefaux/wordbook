package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word_B(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "source_id")  val sourceId: Int = 0,
    @ColumnInfo(name = "lang_id")    val languageId: Int = 0,
    @ColumnInfo(name = "value")      val value: String = "",
)
