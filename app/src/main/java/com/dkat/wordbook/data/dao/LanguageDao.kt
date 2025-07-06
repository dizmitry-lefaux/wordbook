package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.LanguageAndOrder
import com.dkat.wordbook.data.entity.LanguageOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    @Transaction
    suspend fun createLanguage(language: Language) {
        var languageId = createLanguageEntity(language = language)
        createLanguageOrderEntity(LanguageOrder(
            languageId = languageId.toInt(),
            order = languageId.toInt())
        )
    }

    @Insert
    suspend fun createLanguageEntity(language: Language) : Long

    @Insert
    suspend fun createLanguageOrderEntity(languageOrder: LanguageOrder)

    @Query("SELECT * FROM language" +
                   " INNER JOIN language_order ON language._language_id = language_order.language_id" +
                   " ORDER BY language_order.language_order ASC")
    fun readLanguages(): Flow<List<LanguageAndOrder>>

    @Query("SELECT * FROM language" +
                   " INNER JOIN language_order ON language._language_id = language_order.language_id" +
                   " ORDER BY language_order.language_order ASC")
    fun readLanguagesBlocking(): List<LanguageAndOrder>

    @Query("UPDATE language SET language_name = :name WHERE _language_id = :id")
    suspend fun updateLanguage(id: Int, name: String)

    @Query("DELETE FROM language WHERE _language_id = :id")
    suspend fun deleteLanguageById(id: Int)

    @Transaction
    suspend fun updateLanguagesOrder(languages: List<LanguageAndOrder>) {
        languages.forEachIndexed { index, language ->
            updateLanguageAndOrder(languageId = language.language.id,
                                   newOrderIndex = index)
        }
    }

    @Query("UPDATE language_order SET language_order = :newOrderIndex WHERE language_id = :languageId")
    suspend fun updateLanguageAndOrder(languageId: Int, newOrderIndex: Int)
}
