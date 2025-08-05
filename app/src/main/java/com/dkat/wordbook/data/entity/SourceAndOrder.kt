package com.dkat.wordbook.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SourceAndOrder(
    @Embedded val source: Source,
    @Relation(
        parentColumn = "_source_id",
        entityColumn = "source_id"
    )
    val sourceOrder: SourceOrder
) {
    override fun toString(): String {
        return "[lang: ${source.name}; order: ${sourceOrder.order}]"
    }
}
