package com.dkat.wordbook.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
 data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rusValue: String = "",
    val engValue: String = "",
    val sourceName: String = "",
    var sessionWeight: Float = 1f,
    var isInSession: Boolean = false
)
