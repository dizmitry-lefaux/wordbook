package com.dkat.wordbook.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SourceWithWords(
    @Embedded val source: Source,

    @Relation(
        entity = Word::class,
        parentColumn = "_source_id",
        entityColumn = "source_id"
    )
    val words: List<Word>
)
