package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source")
data class Source(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")                       override val id: Int = 0,
    @ColumnInfo(name = "name")                     override val name: String = "",
    @ColumnInfo(name = "main_orig_lang_id")                 val mainOrigLangId: Int = 0,
    @ColumnInfo(name = "main_translation_lang_id")          val mainTranslationLangId: Int = 0,
) : NamedEntity
