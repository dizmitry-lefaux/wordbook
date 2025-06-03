package com.dkat.wordbook.data.repo

import android.content.Context
import com.dkat.wordbook.data.WordDatabase
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import kotlinx.coroutines.flow.Flow

class SessionRepository(private val context: Context) {
    private val sessionDao = WordDatabase.getDatabase(context).sessionDao()

    suspend fun createSession(session: Session): Long {
        return sessionDao.createSession(session)
    }

    suspend fun createSessionSourceCrossRef(sourceId: Int, sessionId: Int) {
        return sessionDao.createSessionSourceCrossRef(sourceId = sourceId, sessionId = sessionId)
    }

    suspend fun updateSession(session: Session) {
        sessionDao.updateSession(session.id, session.name)
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
}
