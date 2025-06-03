package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert
    suspend fun createSession(session: Session): Long

    @Query("INSERT INTO session_source (source_id, session_id) VALUES (:sourceId, :sessionId)")
    suspend fun createSessionSourceCrossRef(sourceId: Int, sessionId: Int)

    // blocking request
    @Query("SELECT * FROM session WHERE id = :id")
    fun readSessionById(id: Int): Session

    @Query("SELECT * FROM source" +
                   " INNER JOIN session_source ON session_source.source_id = source.id" +
                   " INNER JOIN session ON session_source.session_id = session.id" +
                   " WHERE session.id = :sessionId"
    )
    fun readSourcesBySessionId(sessionId: Int): List<Source>

    @Transaction
    suspend fun deleteSession(sessionId: Int) {
        deleteSessionById(sessionId)
        deleteSessionSourceCrossRef(sessionId = sessionId)
    }

    @Query("DELETE FROM session WHERE id = :id")
    suspend fun deleteSessionById(id: Int)

    @Query("DELETE FROM session_source WHERE session_id = :sessionId")
    suspend fun deleteSessionSourceCrossRef(sessionId: Int)

    @Query("SELECT * FROM session")
    fun readSessions(): Flow<List<Session>>

    @Query("UPDATE session SET name = :name WHERE id = :id")
    fun updateSession(id: Int, name: String)

    @Transaction
    suspend fun updateSessionWithSources(session: Session, sources: List<Source>) {
        updateSession(id = session.id, name = session.name)
        deleteSessionSourceCrossRef(sessionId = session.id)
        sources.forEach { source ->
            createSessionSourceCrossRef(sourceId = source.id, sessionId = session.id)
        }
    }
}
