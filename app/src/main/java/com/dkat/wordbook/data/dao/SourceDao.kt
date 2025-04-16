package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dkat.wordbook.data.entity.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Insert
    suspend fun createSource(source: Source)

    @Query("DELETE FROM source WHERE id = :id")
    suspend fun deleteSourceById(id: Int)

    @Query("SELECT * FROM source")
    fun readSources(): Flow<List<Source>>
}
