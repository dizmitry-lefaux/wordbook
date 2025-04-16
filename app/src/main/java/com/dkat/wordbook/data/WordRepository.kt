package com.dkat.wordbook.data

import android.content.Context
import android.util.Log
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val TAG = "WordRepository"

class WordRepository(private val context: Context)
{
    private val wordDao = WordDatabase.getDatabase(context).wordDao()
    private val dataMigrationDao = WordDatabase.getDatabase(context).dataMigrationDao()
    private val sourceDao = WordDatabase.getDatabase(context).sourceDao()
    private val languageDao = WordDatabase.getDatabase(context).languageDao()

    suspend fun createWords(wordList: List<Word>) {
        if (wordList.isNotEmpty()) wordDao.insertWords(wordList)
    }

    // dkat: TODO: update
    suspend fun createWord(word: Word) {
        wordDao.createWord(word)
    }

    // dkat: TODO: move to separate repository
    suspend fun createSource(source: Source) {
        sourceDao.createSource(source)
    }

    // dkat: TODO: move to separate repository
    suspend fun createLanguage(language: Language) {
        languageDao.createLanguage(language)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWordById(id = word.id)
    }

    fun getWords(): Flow<List<Word>> = wordDao.getWords()

    // could be used to run inside of coroutine with no returning value
    fun getSourceWordsFlowList(sourceName: String): Flow<List<Word>> = wordDao.getSourceWordsFlowList(sourceName)

    fun readSourcesStrings(): Flow<List<String>> = wordDao.readSources()

    fun readLanguages(): Flow<List<Language>> = wordDao.readLanguages()

    // could be used to run inside of coroutine returning value
    suspend fun getSourceWords(sourceName: String): List<Word>
    {
        val words = mutableListOf<Word>()
        withContext(Dispatchers.Default) {
            // using suspend function inside
            words.addAll(wordDao.getSourceWords(sourceName = sourceName))
        }
        return words
    }

    suspend fun resetSession()
    {
        Log.i(TAG, "resetting session")
        wordDao.resetSession();
    }

    suspend fun resetIsInSession()
    {
        Log.i(TAG, "resetting isInSession")
        wordDao.resetIsInSession()
    }

    suspend fun setIsInSession(word: Word, isInSession: Boolean)
    {
        Log.i(TAG, "set isInSession: '$isInSession' for words: '$word'")
        wordDao.updateIsInSession(isInSession = isInSession, id = word.id)
    }

    suspend fun setIsInSessionForList(ids: List<Int>, isInSession: Boolean) {
        Log.i(TAG, "set isInSession: '$isInSession' for words with ids: '$ids'")
        wordDao.updateIsInSessionForList(isInSession = isInSession, ids = ids)
    }

    suspend fun setSessionWeight(word: Word, sessionWeight: Float)
    {
        Log.i(TAG, "set sessionWeight: '$sessionWeight' for words: '$word'")
        wordDao.updateSessionWeight(sessionWeight = sessionWeight, id = word.id)
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

    suspend fun migrateSources() {
        dataMigrationDao.migrateSources()
    }

    suspend fun migrateLanguages() {
        dataMigrationDao.migrateLanguages()
    }

    suspend fun migrateWords() {
        dataMigrationDao.migrateWords()
    }

    suspend fun migrateTranslations() {
        dataMigrationDao.migrateTranslations()
    }

    // dkat: TODO: move to separate repository
    suspend fun deleteSource(source: Source) {
        sourceDao.deleteSourceById(id = source.id)
    }

    // dkat: TODO: move to separate repository
    suspend fun deleteLanguage(language: Language) {
        languageDao.deleteLanguageById(id = language.id)
    }

    // dkat: TODO: move to separate repository
    fun readSources(): Flow<List<Source>> = sourceDao.readSources()
}
