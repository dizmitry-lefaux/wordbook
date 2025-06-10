package com.dkat.wordbook.data.repo

import android.content.Context
import android.util.Log
import com.dkat.wordbook.data.WordDatabase
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.SessionWordCrossRef
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import kotlinx.coroutines.flow.Flow

private const val TAG = "SessionRepository"

class SessionRepository(private val context: Context) {
    private val sessionDao = WordDatabase.getDatabase(context).sessionDao()

    suspend fun createSession(session: Session): Long {
        return sessionDao.createSession(session)
    }

    suspend fun createSessionSourceCrossRef(sourceId: Int, sessionId: Int) {
        sessionDao.createSessionSourceCrossRef(sourceId = sourceId, sessionId = sessionId)
    }

    suspend fun createSessionWordCrossRef(sessionWords: List<SessionWordCrossRef>) {
        sessionDao.createSessionWordCrossRef(sessionWords = sessionWords)
    }

    suspend fun updateSessionWithSources(session: Session, sources: List<Source>) {
        sessionDao.updateSessionWithSources(session, sources)
    }

    suspend fun deleteSession(session: Session) {
        sessionDao.deleteSession(sessionId = session.id)
    }

    suspend fun readSession(id: Int): Session {
        return sessionDao.readSessionById(id = id)
    }

    suspend fun readSourcesBySessionId(sessionId: Int) : List<Source> {
        return sessionDao.readSourcesBySessionId(sessionId)
    }

    fun readSessions(): Flow<List<Session>> = sessionDao.readSessions()

    fun readActiveSession(): Flow<Session> = sessionDao.readActiveSession()

    fun readActiveSessionWords(): Flow<List<WordWithTranslations>> {
        Log.i(TAG, "read active session words")
        return sessionDao.readActiveSessionWords()
    }

    fun readCandidateSessionWords(): Flow<List<WordWithTranslations>> {
        Log.i(TAG, "read candidate session words")
        return sessionDao.readCandidateSessionWords()
    }

    fun readSessionWords(): Flow<List<WordWithTranslations>> {
        Log.i(TAG, "read session words")
        return sessionDao.readSessionWords()
    }

    fun makeSessionActive(sessionId: Int) = sessionDao.makeSessionActive(sessionId)

    fun resetSession(session: Session) {
        Log.i(TAG, "reset session: $session")
        sessionDao.resetActiveSession(sessionId = session.id)
    }

    suspend fun setIsInSessionForWordsInSession(wordIds: List<Int>, sessionId: Int, isInSession: Boolean) {
        Log.i(TAG, "set isInSession: '$isInSession' for session: '$sessionId' and words: $wordIds")
        sessionDao.setIsInSessionForWordsInSession(
            wordIds = wordIds, sessionId = sessionId, isInSession = isInSession
        )
    }

    suspend fun setSessionWeightForWordsInSession(wordIds: List<Int>, sessionId: Int, sessionWeight: Float) {
        Log.i(TAG, "set sessionWeight: '$sessionWeight' for session: '$sessionId' and words: $wordIds")
        sessionDao.setSessionWeightForWordsInSession(
            wordIds = wordIds, sessionId = sessionId, sessionWeight = sessionWeight
        )
    }
}
