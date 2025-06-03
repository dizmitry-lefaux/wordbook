package com.dkat.wordbook.viewModel.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.repo.SessionRepository
import com.dkat.wordbook.data.repo.WordRepository
import com.dkat.wordbook.data.repo.WordsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "SessionViewModel"
private const val SESSION_SIZE = 3

class SessionViewModel(
    private val wordsRepository: WordsRepository,
    private val wordRepository: WordRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
//    private val random = Random(0)

    private val _sessions = MutableStateFlow<List<Session>>(emptyList())
    val sessions: StateFlow<List<Session>> = sessionRepository.readSessions().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed((5_000)),
        initialValue = emptyList()
    )

    fun createSession(source: Source, session: Session) {
        var sessionId: Long = 0
        viewModelScope.launch {
            launch { sessionId = sessionRepository.createSession(session = session) }.join()
            launch {
                sessionRepository.createSessionSourceCrossRef(sourceId = source.id, sessionId = sessionId.toInt())
            }.join()
        }
    }

    fun readSession(id: Int): Session {
        var session = Session()
        viewModelScope.launch { launch { session = sessionRepository.readSession(id) }.join() }
        return session
    }

    fun readSourcesBySessionId(sessionId: Int) : List<Source> {
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

//    fun updateSession() {
//        val wordsList = wordsOld.value.filter { word: Word ->
//            word.sessionWeight > 0
//        }
//        updateSession(wordsList)
//    }
//
//    fun restartSession() {
//        viewModelScope.launch {
//            val job1 = launch { resetSession() }
//            job1.join()
//            val job2 = launch { startNewSession() }
//            job2.join()
//        }
//    }
//
//    private fun resetSession() {
//        viewModelScope.launch {
//            val job = launch { wordsRepository.resetSession() }
//            job.join()
//        }
//    }
//
//    private fun resetIsInSession() {
//        viewModelScope.launch {
//            val job = launch { wordsRepository.resetIsInSession() }
//            job.join()
//        }
//    }
//
//    private fun startNewSession() {
//        updateSession(wordsOld.value)
//    }
//
//    private fun updateSession(wordList: List<Word>) {
//        resetIsInSession()
//        val sessionWords = mutableMapOf<Word, Float>()
//        wordList.forEach {
//            val randomSessionWeightMultiplier: Float = random.nextFloat()
//            sessionWords[it] = randomSessionWeightMultiplier
//        }
//        val sortedList = sessionWords.toList().sortedByDescending { it.second }
//
//        val listSize = sortedList.size
//        val sessionSize = if (listSize > SESSION_SIZE) SESSION_SIZE else listSize
//
//        val sessionIds: List<Int> = sortedList.take(sessionSize).stream()
//            .map { pair -> pair.first }
//            .map { words -> words.id }
//            .collect(Collectors.toList())
//
//        viewModelScope.launch {
//            val job1 = launch { wordsRepository.setIsInSessionForList(sessionIds, true) }
//            val job2 = launch { wordsRepository.setSessionWeightForList(sessionIds, 0f) }
//            job1.join()
//            job2.join()
//        }.invokeOnCompletion {
//            viewModelScope.launch(Dispatchers.Main) {
//                _sessionWords.value = wordsRepository.getSessionWords()
//            }
//        }
//    }
}

class SessionViewModelFactory(
    private val wordsRepository: WordsRepository,
    private val wordRepository: WordRepository,
    private val sessionRepository: SessionRepository
) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            return SessionViewModel(wordsRepository = wordsRepository,
                                    wordRepository = wordRepository,
                                    sessionRepository = sessionRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
