package com.dkat.wordbook.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithTranslations(
    @Embedded val word: Word,

    @Relation(
        entity = Translation::class,
        parentColumn = "_word_id",
        entityColumn = "word_id"
    )
    val translations: List<Translation>
)
