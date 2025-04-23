package com.dkat.wordbook.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithTranslations(
    @Embedded val word: Word_B,

    @Relation(
        entity = Translation::class,
        parentColumn = "id",
        entityColumn = "word_id"
    )
    val translations: List<Translation>
)
