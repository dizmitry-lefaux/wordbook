package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao
{
    @Insert
    suspend fun createWordOld(word: Word)

    @Insert
    suspend fun createWord(word: Word_B): Long

    @Insert
    fun insertWords(words: List<Word>)

    @Query("SELECT COUNT(*) from words")
    suspend fun getCount(): Int

    @Query("SELECT * from words")
    fun getWords(): Flow<List<Word>>

    @Query("DELETE FROM words WHERE id = :id")
    suspend fun deleteWordByIdOld(id: Int)

    @Query("DELETE FROM word WHERE id = :id")
    suspend fun deleteWordById(id: Int)

    @Query("SELECT DISTINCT sourceName from words")
    fun readSources(): Flow<List<String>>

    @Query("SELECT * from language")
    fun readLanguages(): Flow<List<Language>>

    @Query("SELECT * FROM words WHERE sourceName = :sourceName")
    fun getSourceWordsFlowList(sourceName: String): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE sourceName = :sourceName")
    suspend fun getSourceWords(sourceName: String): List<Word>

    @Query("UPDATE words SET isInSession = 0, sessionWeight = 1")
    suspend fun resetSession()

    @Query("UPDATE words SET isInSession = 0")
    suspend fun resetIsInSession()

    @Query("SELECT * FROM words WHERE isInSession = 1")
    suspend fun getSessionWords(): List<Word>

    @Query("SELECT * FROM words WHERE isInSession = 1")
    fun getSessionWordsFlow(): Flow<List<Word>>

    @Query("UPDATE words SET sessionWeight = :sessionWeight WHERE id = :id")
    suspend fun updateSessionWeight(sessionWeight: Float, id: Int)

    @Query("UPDATE words SET sessionWeight = :sessionWeight WHERE id IN (:ids)")
    suspend fun updateSessionWeightForList(sessionWeight: Float, ids: List<Int>)

    @Query("UPDATE words SET isInSession = :isInSession WHERE id = :id")
    suspend fun updateIsInSession(isInSession: Boolean, id: Int)

    @Query("UPDATE words SET isInSession = :isInSession WHERE id IN (:ids)")
    suspend fun updateIsInSessionForList(isInSession: Boolean, ids: List<Int>)

    @Transaction
    @Query("SELECT * FROM word")
    fun readWordsWithTranslations(): Flow<List<WordWithTranslations>>
}
