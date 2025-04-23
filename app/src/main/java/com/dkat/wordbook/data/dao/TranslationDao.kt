package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.dkat.wordbook.data.entity.Translation

@Dao
interface TranslationDao {

    @Insert
    suspend fun createTranslation(translation: Translation): Long
}
