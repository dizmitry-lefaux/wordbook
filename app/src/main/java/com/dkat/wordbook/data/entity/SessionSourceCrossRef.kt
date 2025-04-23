package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "session_source",
    primaryKeys = ["session_id", "source_id"],
)
data class SessionSourceCrossRef(
    @ColumnInfo(name = "session_id") val sessionId: Int = 0,
    @ColumnInfo(name = "source_id")  val sourceId: Int = 0
)

data class SessionWithSources(
    @Embedded val session: Session,
    @Relation(
        parentColumn = "id",
        entityColumn = "session_id",
        associateBy = Junction(SessionSourceCrossRef::class)
    )
    val sources: List<Source>
)

data class SourceWithSessions(
    @Embedded val source: Source,
    @Relation(
        parentColumn = "id",
        entityColumn = "source_id",
        associateBy = Junction(SessionSourceCrossRef::class)
    )
    val sessions: List<Session>
)
