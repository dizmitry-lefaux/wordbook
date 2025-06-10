package com.dkat.wordbook.viewModel.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.SessionWordCrossRef
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.repo.SessionRepository
import com.dkat.wordbook.data.repo.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Random
import java.util.stream.Collectors.toList

private const val TAG = "SessionViewModel"
private const val SESSION_SIZE = 3

class SessionViewModel(
    private val wordRepository: WordRepository,
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    private val random = Random(0)

    private val _sessions = MutableStateFlow<List<Session>>(emptyList())
    val sessions: StateFlow<List<Session>> = sessionRepository.readSessions().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed((5_000)),
        initialValue = _sessions.value
    )

    private val _selectedSession = MutableStateFlow(Session())
    val selectedSession: StateFlow<Session> = sessionRepository.readActiveSession().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _selectedSession.value
    )

    fun updateSelectedSession(session: Session) {
        _selectedSession.value = session
        viewModelScope.launch { launch { sessionRepository.makeSessionActive(session.id) }.join() }
    }

    private val _selectedSessionActiveWords = MutableStateFlow(listOf<WordWithTranslations>())
    val selectedSessionActiveWords: StateFlow<List<WordWithTranslations>> =
        sessionRepository.readActiveSessionWords().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = _selectedSessionActiveWords.value
        )

    fun createSession(source: Source, session: Session) {
        var sessionId: Long = 0
        var sourceWords: List<Word> = mutableListOf()
        val sessionWords = mutableListOf<SessionWordCrossRef>()
        viewModelScope.launch {
            launch { sessionId = sessionRepository.createSession(session = session) }.join()
            launch {
                sessionRepository.createSessionSourceCrossRef(sourceId = source.id,
                                                              sessionId = sessionId.toInt()
                )
            }.join()
            launch {
                launch {
                    sourceWords = wordRepository.readWordsBySourceId(sourceId = source.id)
                }.join()
                sourceWords.stream().forEach { sourceWord ->
                    sessionWords.add(
                        SessionWordCrossRef(wordId = sourceWord.id,
                                            sessionId = sessionId.toInt(),
                                            sessionWeight = 1.0f,
                                            isActive = false
                        )
                    )
                }
            }.join()
            launch {
                sessionRepository.createSessionWordCrossRef(sessionWords)
            }.join()
        }
    }

    fun readSession(id: Int): Session {
        var session = Session()
        viewModelScope.launch { launch { session = sessionRepository.readSession(id) }.join() }
        return session
    }

    fun readSourcesBySessionId(sessionId: Int): List<Source> {
        var sources = emptyList<Source>()
        viewModelScope.launch {
            launch { sources = sessionRepository.readSourcesBySessionId(sessionId) }.join()
        }
        return sources
    }

    fun updateSessionWithSources(session: Session, sources: List<Source>) {
        viewModelScope.launch {
            launch { sessionRepository.updateSessionWithSources(session, sources) }.join()
        }
    }

    fun deleteSession(session: Session) {
        viewModelScope.launch {
            launch { sessionRepository.deleteSession(session) }
        }
    }

    fun updateSession() {
        viewModelScope.launch {
            updateSession(candidateWords = sessionRepository.readCandidateSessionWords().first(),
                          activeWords = sessionRepository.readActiveSessionWords().first())
        }
    }

    private fun updateSession(candidateWords: List<WordWithTranslations>, activeWords: List<WordWithTranslations>) {
        Log.i(TAG, "update session: $candidateWords")
        val sessionWords = mutableMapOf<WordWithTranslations, Float>()
        Log.i(TAG, "sessionWords 1: $sessionWords")
        candidateWords.forEach {
            val randomSessionWeightMultiplier: Float = random.nextFloat()
            sessionWords[it] = randomSessionWeightMultiplier
        }
        Log.i(TAG, "sessionWords 2: $sessionWords")
        val sortedList = sessionWords.toList().sortedByDescending { it.second }
        Log.i(TAG, "sortedList 1: $sortedList")

        val listSize = sortedList.size
        val sessionSize = if (listSize > SESSION_SIZE) SESSION_SIZE else listSize
        Log.i(TAG, "sessionSize: $sessionSize")

        val wordIds: List<Int> = sortedList.take(sessionSize).stream()
            .map { pair -> pair.first }
            .map { word -> word.word.id }
            .collect(toList())
        Log.i(TAG, "wordIds: $wordIds")

        viewModelScope.launch {
            launch { sessionRepository.setIsInSessionForWordsInSession(
                wordIds = wordIds,
                sessionId = selectedSession.value.id,
                isInSession = true)
            }.join()
            launch { sessionRepository.setIsInSessionForWordsInSession(
                wordIds = activeWords.map { wordWithTranslations -> wordWithTranslations.word.id }.toList(),
                sessionId = selectedSession.value.id,
                isInSession = false)
            }.join()
            launch { sessionRepository.setSessionWeightForWordsInSession(
                wordIds = wordIds,
                sessionId = selectedSession.value.id,
                sessionWeight = 0f)
            }.join()
        }.invokeOnCompletion {
            viewModelScope.launch(Dispatchers.Main) {
                val wordsOnCompletion = sessionRepository.readActiveSessionWords().first()
                Log.i(TAG, "on completion")
                Log.i(TAG, "active session words: $wordsOnCompletion")
                _selectedSessionActiveWords.value = wordsOnCompletion
            }
        }
    }

    fun restartSession() {
        viewModelScope.launch {
            launch { resetSession() }.join()
            launch { startNewSession() }.join()
        }
    }

    private fun resetSession() {
        Log.i(TAG, "reset session")
        viewModelScope.launch {
            launch { sessionRepository.resetSession(selectedSession.value) }.join()
        }
    }

    private suspend fun startNewSession() {
        Log.i(TAG, "start new session")
        val words = mutableListOf<WordWithTranslations>()
        viewModelScope.launch {
            launch {
                words.addAll(sessionRepository.readSessionWords().first())
            }.join()
            launch {
                updateSession(words, emptyList())
            }.join()
        }
    }
}

class SessionViewModelFactory(
    private val wordRepository: WordRepository,
    private val sessionRepository: SessionRepository,
) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            return SessionViewModel(
                wordRepository = wordRepository,
                sessionRepository = sessionRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
