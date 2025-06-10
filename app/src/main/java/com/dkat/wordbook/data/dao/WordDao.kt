package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert
    suspend fun createWord(word: Word): Long

    @Query("UPDATE word SET word_value = :value WHERE _word_id = :id")
    suspend fun updateWord(id: Int, value: String)

    @Query("DELETE FROM word WHERE _word_id = :id")
    suspend fun deleteWordById(id: Int)

    @Transaction
    @Query("SELECT * FROM word")
    fun readWordsWithTranslations(): Flow<List<WordWithTranslations>>

    @Query("SELECT * FROM word WHERE word.source_id = :sourceId")
    fun readWordsBySourceId(sourceId: Int): List<Word>
}
