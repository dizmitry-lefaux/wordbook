package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dkat.wordbook.data.entity.Translation

@Dao
interface TranslationDao {

    @Insert
    suspend fun createTranslation(translation: Translation): Long

    @Query("DELETE FROM translation WHERE word_id = :wordId")
    suspend fun deleteTranslationsByWordId(wordId: Int)
}
