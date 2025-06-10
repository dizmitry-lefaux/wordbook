package com.dkat.wordbook.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class OrigLanguageWithSources(
    @Embedded val word: Language,

    @Relation(
        entity = Source::class,
        parentColumn = "_language_id",
        entityColumn = "main_orig_lang_id"
    )
    val sources: List<Source>
)
