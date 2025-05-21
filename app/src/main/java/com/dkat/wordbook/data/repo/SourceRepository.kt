package com.dkat.wordbook.data.repo

import android.content.Context
import com.dkat.wordbook.data.WordDatabase
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import kotlinx.coroutines.flow.Flow

class SourceRepository(private val context: Context) {
    private val sourceDao = WordDatabase.getDatabase(context).sourceDao()

    suspend fun createSource(source: Source): Long {
        return sourceDao.createSource(source)
    }

    suspend fun updateSource(source: Source) {
        sourceDao.updateSource(source.id, source.name, source.mainOrigLangId, source.mainTranslationLangId)
    }

    suspend fun deleteSource(source: Source) {
        sourceDao.deleteSourceById(id = source.id)
    }

    fun readSource(id: Int): Source {
        return sourceDao.readSourceById(id = id)
    }

    fun readSources(): Flow<List<Source>> = sourceDao.readSources()

    fun readSourcesWithWords(): Flow<List<SourceWithWords>> = sourceDao.readSourcesWithWords()
}
