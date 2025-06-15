package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.SessionWordCrossRef
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert
    suspend fun createSession(session: Session): Long

    @Query("INSERT INTO session_source (source_id, session_id) VALUES (:sourceId, :sessionId)")
    suspend fun createSessionSourceCrossRef(sourceId: Int, sessionId: Int)

    @Insert
    suspend fun createSessionWordCrossRef(sessionWords: List<SessionWordCrossRef>)

    // blocking request
    @Query("SELECT * FROM session WHERE _session_id = :id")
    fun readSessionById(id: Int): Session

    @Query("SELECT * FROM source" +
                   " INNER JOIN session_source ON session_source.source_id = source._source_id" +
                   " INNER JOIN session ON session_source.session_id = session._session_id" +
                   " WHERE session._session_id = :sessionId"
    )
    fun readSourcesBySessionId(sessionId: Int): List<Source>

    @Transaction
    suspend fun deleteSession(sessionId: Int) {
        deleteSessionById(sessionId)
        deleteSessionSourceCrossRef(sessionId = sessionId)
        deleteSessionWordCrossRef(sessionId = sessionId)
    }

    @Query("DELETE FROM session WHERE _session_id = :id")
    suspend fun deleteSessionById(id: Int)

    @Query("DELETE FROM session_source WHERE session_id = :sessionId")
    suspend fun deleteSessionSourceCrossRef(sessionId: Int)

    @Query("DELETE FROM session_word WHERE session_id = :sessionId")
    suspend fun deleteSessionWordCrossRef(sessionId: Int)

    @Query("SELECT * FROM session")
    fun readSessions(): Flow<List<Session>>

    @Query("SELECT * FROM session WHERE is_session_active = 1")
    fun readActiveSession(): Flow<Session>

    @Transaction
    suspend fun updateSessionWithSources(session: Session, sources: List<Source>) {
        updateSession(id = session.id, name = session.name)
        deleteSessionSourceCrossRef(sessionId = session.id)
        sources.forEach { source ->
            createSessionSourceCrossRef(sourceId = source.id, sessionId = session.id)
        }
    }

    @Query("SELECT * FROM session_word"+
                   " INNER JOIN word ON session_word.word_id = word._word_id" +
                   " INNER JOIN session ON session_word.session_id = session._session_id" +
                   " WHERE session.is_session_active = 1" +
                   " AND session_word.is_word_active = 1")
    fun readActiveSessionWords(): Flow<List<WordWithTranslations>>

    @Query("SELECT * FROM session_word"+
                   " INNER JOIN word ON session_word.word_id = word._word_id" +
                   " INNER JOIN session ON session_word.session_id = session._session_id" +
                   " WHERE session.is_session_active = 1" +
                   " AND session_word.is_word_active = 0" +
                   " AND session_word.session_weight != 0")
    fun readCandidateSessionWords(): Flow<List<WordWithTranslations>>

    @Query("SELECT * FROM word"+
                   " INNER JOIN session_word ON session_word.word_id = word._word_id" +
                   " INNER JOIN session ON session_word.session_id = session._session_id" +
                   " WHERE session.is_session_active = 1")
    fun readSessionWords(): Flow<List<WordWithTranslations>>

    @Query("UPDATE session SET session_name = :name WHERE _session_id = :id")
    fun updateSession(id: Int, name: String)

    @Transaction
    fun makeSessionActive(sessionId: Int) {
        privateMakeSessionsInactive(activeSessionId = sessionId)
        privateMakeSessionActive(activeSessionId = sessionId)
    }

    @Query("UPDATE session_word SET is_word_active = 0, session_weight = 1 WHERE session_id = :sessionId")
    fun resetActiveSession(sessionId: Int)

    @Query("UPDATE session SET is_session_active = 1 WHERE _session_id = :activeSessionId")
    fun privateMakeSessionActive(activeSessionId: Int)

    @Query("UPDATE session SET is_session_active = 0 WHERE _session_id != :activeSessionId")
    fun privateMakeSessionsInactive(activeSessionId: Int)

    @Query("UPDATE session_word SET is_word_active = :isInSession " +
                   "WHERE session_id = :sessionId AND word_id IN (:wordIds)")
    fun setIsInSessionForWordsInSession(wordIds: List<Int>, sessionId: Int, isInSession: Boolean)

    @Query("UPDATE session_word SET session_weight = :sessionWeight " +
                   "WHERE session_id = :sessionId AND word_id IN (:wordIds)")
    fun setSessionWeightForWordsInSession(wordIds: List<Int>, sessionId: Int, sessionWeight: Float)
}
