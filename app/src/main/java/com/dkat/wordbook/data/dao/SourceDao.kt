package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceAndOrder
import com.dkat.wordbook.data.entity.SourceOrder
import com.dkat.wordbook.data.entity.SourceWithWords
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Transaction
    suspend fun createSource(source: Source): Long {
        var sourceId = createSourceEntity(source = source)
        createSourceOrderEntity(SourceOrder(
            sourceId = sourceId.toInt(),
            order = sourceId.toInt())
        )
        return sourceId
    }

    @Insert
    suspend fun createSourceEntity(source: Source) : Long

    @Insert
    suspend fun createSourceOrderEntity(sourceOrder: SourceOrder)

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

    @Query("SELECT * FROM source" +
                   " INNER JOIN source_order ON source._source_id = source_order.source_id" +
                   " ORDER BY source_order.source_order ASC")
    fun readSourcesBlocking(): List<SourceAndOrder>

    @Query("UPDATE source SET " +
                   "source_name = :name, " +
                   "main_orig_lang_id = :mainOrigLangId, " +
                   "main_translation_lang_id = :mainTranslationLangId " +
                   "WHERE _source_id = :id")
    fun updateSource(id: Int, name: String, mainOrigLangId: Int, mainTranslationLangId: Int)

    @Transaction
    suspend fun updateSourcesOrder(sources: List<SourceAndOrder>) {
        sources.forEachIndexed { index, source ->
            updateSourceAndOrder(sourceId = source.source.id,
                                   newOrderIndex = index)
        }
    }

    @Query("UPDATE source_order SET source_order = :newOrderIndex WHERE source_id = :sourceId")
    suspend fun updateSourceAndOrder(sourceId: Int, newOrderIndex: Int)
}
