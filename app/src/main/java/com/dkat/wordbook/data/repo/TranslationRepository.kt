package com.dkat.wordbook.data.repo

import android.content.Context
import com.dkat.wordbook.data.WordDatabase
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B

class TranslationRepository(private val context: Context) {
    private val translationDao = WordDatabase.getDatabase(context).translationDao()

    suspend fun createTranslation(translation: Translation): Long {
        return translationDao.createTranslation(translation)
    }

    suspend fun deleteTranslationsByWordId(word: Word_B) {
        return translationDao.deleteTranslationsByWordId(wordId = word.id)
    }
}
