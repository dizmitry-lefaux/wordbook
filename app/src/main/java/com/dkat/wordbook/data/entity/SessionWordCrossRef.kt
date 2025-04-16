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
    @ColumnInfo(name = "session_weight") val sessionWeight: Float = 1f
)

data class SessionWithWords(
    @Embedded val session: Session,
    @Relation(
        parentColumn = "id",
        entityColumn = "session_id",
        associateBy = Junction(SessionWordCrossRef::class)
    )
    val words: List<Word_B>
)

data class WordWithSessions(
    @Embedded val word: Word_B,
    @Relation(
        parentColumn = "id",
        entityColumn = "word_id"
    )
    val sessions: List<Session>
)
