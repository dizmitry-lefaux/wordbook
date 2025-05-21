package com.dkat.wordbook.data.repo

import android.content.Context
import android.util.Log
import com.dkat.wordbook.data.WordDatabase
import com.dkat.wordbook.data.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val TAG = "WordRepository"

class WordsRepository(private val context: Context) {
    private val wordsDao = WordDatabase.getDatabase(context).wordsDao()

    fun getWords(): Flow<List<Word>> = wordsDao.getWords()

    // could be used to run inside of coroutine returning value
    suspend fun getSourceWords(sourceName: String): List<Word> {
        val words = mutableListOf<Word>()
        withContext(Dispatchers.Default) {
            // using suspend function inside
            words.addAll(wordsDao.getSourceWords(sourceName = sourceName))
        }
        return words
    }

    suspend fun resetSession() {
        Log.i(TAG, "resetting session")
        wordsDao.resetSession();
    }

    suspend fun resetIsInSession() {
        Log.i(TAG, "resetting isInSession")
        wordsDao.resetIsInSession()
    }

    suspend fun setIsInSessionForList(ids: List<Int>, isInSession: Boolean) {
        Log.i(TAG, "set isInSession: '$isInSession' for words with ids: '$ids'")
        wordsDao.updateIsInSessionForList(isInSession = isInSession, ids = ids)
    }

    suspend fun setSessionWeightForList(ids: List<Int>, sessionWeight: Float) {
        Log.i(TAG, "set sessionWeight: '$sessionWeight' for words with ids: '$ids'")
        wordsDao.updateSessionWeightForList(sessionWeight = sessionWeight, ids = ids)
    }

    suspend fun getSessionWords(): List<Word>
    {
        val sessionWords = mutableListOf<Word>()
        withContext(Dispatchers.Default) {
            sessionWords.addAll(wordsDao.getSessionWords())
        }
        return sessionWords
    }

    fun getSessionWordsFlow(): Flow<List<Word>> = wordsDao.getSessionWordsFlow()
}
