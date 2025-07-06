package com.dkat.wordbook.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class LanguageAndOrder(
    @Embedded val language: Language,
    @Relation(
        parentColumn = "_language_id",
        entityColumn = "language_id"
    )
    val languageOrder: LanguageOrder
) {
    override fun toString(): String {
        return "[lang: ${language.name}; order: ${languageOrder.order}]"
    }
}
