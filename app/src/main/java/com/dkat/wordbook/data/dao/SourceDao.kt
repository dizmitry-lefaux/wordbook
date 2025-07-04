package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Insert
    suspend fun createSource(source: Source): Long

    // blocking request
    @Query("SELECT * FROM source WHERE _source_id = :id")
    fun readSourceById(id: Int): Source

    @Query("DELETE FROM source WHERE _source_id = :id")
    suspend fun deleteSourceById(id: Int)

    @Query("SELECT * FROM source")
    fun readSources(): Flow<List<Source>>

    @Transaction
    @Query("SELECT * FROM source")
    fun readSourcesWithWords(): Flow<List<SourceWithWords>>

    @Query("UPDATE source SET " +
                   "source_name = :name, " +
                   "main_orig_lang_id = :mainOrigLangId, " +
                   "main_translation_lang_id = :mainTranslationLangId " +
                   "WHERE _source_id = :id")
    fun updateSource(id: Int, name: String, mainOrigLangId: Int, mainTranslationLangId: Int)
}
