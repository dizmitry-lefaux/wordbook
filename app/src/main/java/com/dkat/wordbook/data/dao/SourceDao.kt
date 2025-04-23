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
    @Query("SELECT * FROM source WHERE id = :id")
    fun readSourceById(id: Int): Source

    @Query("DELETE FROM source WHERE id = :id")
    suspend fun deleteSourceById(id: Int)

    @Query("SELECT * FROM source")
    fun readSources(): Flow<List<Source>>

    @Transaction
    @Query("SELECT * FROM source")
    fun readSourcesWithWords(): Flow<List<SourceWithWords>>
}
