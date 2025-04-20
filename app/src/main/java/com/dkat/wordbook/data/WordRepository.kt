package com.dkat.wordbook.data

import android.content.Context
import android.util.Log
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val TAG = "WordRepository"

class WordRepository(private val context: Context)
{
    private val wordDao = WordDatabase.getDatabase(context).wordDao()
    private val translationDao = WordDatabase.getDatabase(context).translationDao()
    private val sourceDao = WordDatabase.getDatabase(context).sourceDao()
    private val languageDao = WordDatabase.getDatabase(context).languageDao()

    suspend fun createWord(word: Word_B): Long {
        return wordDao.createWord(word)
    }

    // TODO: move to separate repository
    suspend fun createTranslation(translation: Translation): Long {
        return translationDao.createTranslation(translation)
    }

    // TODO: move to separate repository
    suspend fun createSource(source: Source): Long {
        return sourceDao.createSource(source)
    }

    // TODO: move to separate repository
    suspend fun createLanguage(language: Language) {
        languageDao.createLanguage(language)
    }

    suspend fun deleteWord(word: Word_B) {
        wordDao.deleteWordById(id = word.id)
    }

    fun getWords(): Flow<List<Word>> = wordDao.getWords()

    fun readLanguages(): Flow<List<Language>> = wordDao.readLanguages()

    // could be used to run inside of coroutine returning value
    suspend fun getSourceWords(sourceName: String): List<Word> {
        val words = mutableListOf<Word>()
        withContext(Dispatchers.Default) {
            // using suspend function inside
            words.addAll(wordDao.getSourceWords(sourceName = sourceName))
        }
        return words
    }

    suspend fun resetSession() {
        Log.i(TAG, "resetting session")
        wordDao.resetSession();
    }

    suspend fun resetIsInSession() {
        Log.i(TAG, "resetting isInSession")
        wordDao.resetIsInSession()
    }

    suspend fun setIsInSessionForList(ids: List<Int>, isInSession: Boolean) {
        Log.i(TAG, "set isInSession: '$isInSession' for words with ids: '$ids'")
        wordDao.updateIsInSessionForList(isInSession = isInSession, ids = ids)
    }

    suspend fun setSessionWeightForList(ids: List<Int>, sessionWeight: Float) {
        Log.i(TAG, "set sessionWeight: '$sessionWeight' for words with ids: '$ids'")
        wordDao.updateSessionWeightForList(sessionWeight = sessionWeight, ids = ids)
    }

    suspend fun getSessionWords(): List<Word>
    {
        val sessionWords = mutableListOf<Word>()
        withContext(Dispatchers.Default) {
            sessionWords.addAll(wordDao.getSessionWords())
        }
        return sessionWords
    }

    fun getSessionWordsFlow(): Flow<List<Word>> = wordDao.getSessionWordsFlow()

    // TODO: move to separate repository
    suspend fun deleteSource(source: Source) {
        sourceDao.deleteSourceById(id = source.id)
    }

    // TODO: move to separate repository
    suspend fun deleteLanguage(language: Language) {
        languageDao.deleteLanguageById(id = language.id)
    }

    // TODO: move to separate repository
    fun readSources(): Flow<List<Source>> = sourceDao.readSources()

    fun readSourcesWithWords(): Flow<List<SourceWithWords>> = sourceDao.readSourcesWithWords()

    fun readWordsWithTranslations(): Flow<List<WordWithTranslations>> = wordDao.readWordsWithTranslations()
}
