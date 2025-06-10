package com.dkat.wordbook.data.repo

import android.content.Context
import com.dkat.wordbook.data.WordDatabase
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val context: Context) {
    private val wordDao = WordDatabase.getDatabase(context).wordDao()

    suspend fun createWord(word: Word): Long {
        return wordDao.createWord(word)
    }

    suspend fun updateWord(word: Word) {
        wordDao.updateWord(word.id, word.value)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWordById(id = word.id)
    }

    fun readWordsWithTranslations(): Flow<List<WordWithTranslations>> = wordDao.readWordsWithTranslations()

    fun readWordsBySourceId(sourceId: Int): List<Word> {
        return wordDao.readWordsBySourceId(sourceId = sourceId)
    }
}
