package com.dkat.wordbook.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface DataMigrationDao
{
    @Transaction
    @Query("INSERT INTO source(name) SELECT DISTINCT sourceName FROM words")
    suspend fun migrateSources()

    @Transaction
    @Query("INSERT INTO language(name) VALUES ('russian'), ('english')")
    suspend fun migrateLanguages()

    @Transaction
    @Query("""
        INSERT INTO word(
            source_id, lang_id, value
        )
        SELECT source.id, language.id, words.rusValue
        FROM words, language
        INNER JOIN source on words.sourceName = source.name
        WHERE language.name = ('russian')""")
    suspend fun migrateWords()

    @Transaction
    @Query("""
        INSERT INTO translation(
            word_id, value, lang_id
        )
        SELECT word.id, words.engValue, language.id
        from word, language
        INNER JOIN words on words.rusValue = word.value
        WHERE language.name = ('english')
    """)
    suspend fun migrateTranslations()

    @Transaction
    @Query("""
        INSERT INTO source(
            main_orig_lang_id
        )
        SELECT id
        FROM language
        WHERE language.name = ('english')
    """)
    suspend fun migrationOrigSourceLanguage()

    @Transaction
    @Query("""
        INSERT INTO source(
            main_translation_lang_id
        )
        SELECT id
        FROM language
        WHERE language.name = ('russian')
    """)
    suspend fun migrationTranslationSourceLanguage()
}
