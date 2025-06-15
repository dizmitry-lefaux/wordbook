package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dkat.wordbook.data.entity.Language
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    @Insert
    suspend fun createLanguage(language: Language)

    @Query("SELECT * from language")
    fun readLanguages(): Flow<List<Language>>

    @Query("UPDATE language SET language_name = :name WHERE _language_id = :id")
    suspend fun updateLanguage(id: Int, name: String)

    @Query("DELETE FROM language WHERE _language_id = :id")
    suspend fun deleteLanguageById(id: Int)
}
