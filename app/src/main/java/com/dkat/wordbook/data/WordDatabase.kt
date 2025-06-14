package com.dkat.wordbook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dkat.wordbook.data.dao.LanguageDao
import com.dkat.wordbook.data.dao.SessionDao
import com.dkat.wordbook.data.dao.SourceDao
import com.dkat.wordbook.data.dao.TranslationDao
import com.dkat.wordbook.data.dao.WordDao
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.SessionSourceCrossRef
import com.dkat.wordbook.data.entity.SessionWordCrossRef
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word

@Database(
    entities = [Language::class, SessionSourceCrossRef::class, SessionWordCrossRef::class,
        Source::class, Translation::class, Word::class, Session::class],
    version = 25,
    exportSchema = false,
)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun translationDao(): TranslationDao
    abstract fun sourceDao(): SourceDao
    abstract fun languageDao(): LanguageDao
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context = context,
                        klass = WordDatabase::class.java,
                        name = "words.db",
                    )
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .addMigrations(MIGRATION_3_4)
                        .addMigrations(MIGRATION_4_5)
                        .addMigrations(MIGRATION_5_6)
                        .addMigrations(MIGRATION_6_7)
                        .addMigrations(MIGRATION_10_11)
                        .addMigrations(MIGRATION_11_12)
                        .addMigrations(MIGRATION_12_13)
                        .addMigrations(MIGRATION_13_14)
                        .addMigrations(MIGRATION_14_15)
                        .addMigrations(MIGRATION_15_16)
                        .addMigrations(MIGRATION_16_17)
                        .addMigrations(MIGRATION_17_18)
                        .addMigrations(MIGRATION_18_19)
                        .addMigrations(MIGRATION_19_20)
                        .addMigrations(MIGRATION_20_21)
                        .addMigrations(MIGRATION_21_22)
                        .addMigrations(MIGRATION_23_24)
                        .addMigrations(MIGRATION_24_25)
                        // allowing to get direct requests blocking UI
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE words ADD isInSession INTEGER NOT NULL DEFAULT(0)")
    }
}

private val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE words ADD sessionWeight REAL NOT NULL DEFAULT(1)")
    }
}

private val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE words RENAME COLUMN setName TO sourceName")
    }
}

private val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE IF NOT EXISTS word(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    source_id INTEGER NOT NULL DEFAULT(0),
                    lang_id INTEGER NOT NULL DEFAULT(0),
                    value TEXT NOT NULL UNIQUE,
                    FOREIGN KEY(source_id) REFERENCES source(id) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY(lang_id) REFERENCES language(id) ON UPDATE CASCADE
                );
                CREATE TABLE IF NOT EXISTS source(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE
                );
                CREATE TABLE IF NOT EXISTS language(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE
                )
                CREATE TABLE IF NOT EXISTS session(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE,
                    is_active INTEGER NOT NULL DEFAULT(0)
                )
                CREATE TABLE IF NOT EXISTS translation(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    word_id INTEGER NOT NULL DEFAULT(0),
                    value TEXT NOT NULL,
                    lang_id INTEGER NOT NULL DEFAULT(0),
                    FOREIGN KEY(word_id) REFERENCES word(id) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY(lang_id) REFERENCES language(id) ON UPDATE CASCADE
                )
                CREATE TABLE IF NOT EXISTS session_source(
                    session_id INTEGER NOT NULL,
                    source_id INTEGER NOT NULL,
                    PRIMARY KEY(session_id, source_id),
                    FOREIGN KEY(session_id) REFERENCES session(id) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY(source_id) REFERENCES source(id) ON DELETE CASCADE ON UPDATE CASCADE
                )
                CREATE TABLE IF NOT EXISTS session_word(
                    word_id INTEGER NOT NULL,
                    session_id INTEGER NOT NULL,
                    session_weight REAL NOT NULL DEFAULT(1),
                    PRIMARY KEY(word_id, session_id),
                    FOREIGN KEY(word_id) REFERENCES word(id) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY(session_id) REFERENCES session(id) ON DELETE CASCADE ON UPDATE CASCADE
                );
            """.trimIndent()
        )
    }
}

private val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE source ADD main_orig_lang_id INTEGER NOT NULL DEFAULT(0)")
    }
}

private val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE source ADD main_translation_lang_id INTEGER NOT NULL DEFAULT(0)")
    }
}

private val MIGRATION_10_11 = object : Migration(10, 11) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE session RENAME COLUMN id TO session_id")
    }
}

private val MIGRATION_11_12 = object : Migration(11, 12) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE word RENAME COLUMN id TO _word_id")
    }
}

private val MIGRATION_12_13 = object : Migration(12, 13) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE session RENAME COLUMN session_id TO _session_id")
    }
}

private val MIGRATION_13_14 = object : Migration(13, 14) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE language RENAME COLUMN id TO _language_id")
    }
}

private val MIGRATION_14_15 = object : Migration(14, 15) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE source RENAME COLUMN id TO _source_id")
    }
}

private val MIGRATION_15_16 = object : Migration(15, 16) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE translation RENAME COLUMN id TO _translation_id")
    }
}

private val MIGRATION_16_17 = object : Migration(16, 17) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE translation RENAME COLUMN value TO translation_value")
    }
}

private val MIGRATION_17_18 = object : Migration(17, 18) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE word RENAME COLUMN value TO word_value")
    }
}

private val MIGRATION_18_19 = object : Migration(18, 19) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE words")
    }
}

private val MIGRATION_19_20 = object : Migration(19, 20) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE language RENAME COLUMN name TO language_name")
    }
}

private val MIGRATION_20_21 = object : Migration(20, 21) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE session RENAME COLUMN name TO session_name")
    }
}

private val MIGRATION_21_22 = object : Migration(21, 22) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE session RENAME COLUMN is_active TO is_session_active")
    }
}

private val MIGRATION_23_24 = object : Migration(23, 24) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE session_word RENAME COLUMN is_active TO is_word_active")
    }
}

private val MIGRATION_24_25 = object : Migration(24, 25) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE source RENAME COLUMN name TO source_name")
    }
}
