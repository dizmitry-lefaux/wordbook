package com.dkat.wordbook.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SourceWithWords(
    @Embedded val source: Source,

    @Relation(
        entity = Word_B::class,
        parentColumn = "id",
        entityColumn = "source_id"
    )
    val words: List<Word_B>
)
