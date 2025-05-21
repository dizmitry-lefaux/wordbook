package com.dkat.wordbook.data.repo

import android.content.Context
import com.dkat.wordbook.data.WordDatabase
import com.dkat.wordbook.data.entity.Language
import kotlinx.coroutines.flow.Flow

class LanguageRepository(private val context: Context) {
    private val languageDao = WordDatabase.getDatabase(context).languageDao()

    suspend fun createLanguage(language: Language) {
        languageDao.createLanguage(language)
    }

    fun readLanguages(): Flow<List<Language>> = languageDao.readLanguages()

    suspend fun updateLanguage(language: Language) {
        languageDao.updateLanguage(language.id, language.name)
    }

    suspend fun deleteLanguage(language: Language) {
        languageDao.deleteLanguageById(id = language.id)
    }
}
