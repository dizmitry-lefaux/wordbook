package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "session_word",
    primaryKeys = ["word_id", "session_id"]
)
data class SessionWordCrossRef(
    @ColumnInfo(name = "word_id")        val wordId: Int = 0,
    @ColumnInfo(name = "session_id")     val sessionId: Int = 0,
    @ColumnInfo(name = "session_weight") val sessionWeight: Float = 1f,
    @ColumnInfo(name = "is_active")      val isActive: Boolean = false
)

data class SessionWithWords(
    @Embedded val session: Session,
    @Relation(
        parentColumn = "_session_id",
        entityColumn = "_word_id",
        associateBy = Junction(SessionWordCrossRef::class)
    )
    val words: List<Word>
)

data class WordWithSessions(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "_word_id",
        entityColumn = "_session_id"
    )
    val sessions: List<Session>
)
