package com.dkat.wordbook.data

import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.LanguageAndOrder
import com.dkat.wordbook.data.entity.LanguageOrder
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceAndOrder
import com.dkat.wordbook.data.entity.SourceOrder
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word

class PreviewData {
    companion object {
        val word1 = Word(id = 4428, sourceId = 4887, languageId = 9685, value = "dictas")
        val word2 = Word(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam")
        val word3 = Word(id = 6850, sourceId = 1486, languageId = 3567, value = "efficiantur")
        val word4 = Word(id = 3317, sourceId = 5259, languageId = 7817, value = "utroque")
        val word5 = Word(id = 8936, sourceId = 2208, languageId = 9823, value = "taciti")
        val translation1 = Translation(id = 2203, wordId = 7627, value = "duo", languageId = 4738)
        val translation2 =  Translation(id = 8867, wordId = 5186, value = "necessitatibus", languageId = 6808)
        val translation3 = Translation(id = 7213, wordId = 5834, value = "deterruisset", languageId = 7215)
        val translation4 = Translation(id = 5050, wordId = 3924, value = "detraxit", languageId = 2641)
        val translation5 = Translation(id = 3907, wordId = 7219, value = "hendrerit", languageId = 5243)
        val translation6 = Translation(id = 1393, wordId = 2937, value = "consequat", languageId = 4081)
        val translation7 = Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243)
        val translation8 = Translation(id = 6495, wordId = 7035, value = "consequat", languageId = 9676)
        val translations = listOf(translation1, translation2, translation3)
        val wordWithTranslations1 = WordWithTranslations(
            word = word1,
            translations = listOf(translation1, translation2, translation3)
        )
        val wordWithTranslations2 = WordWithTranslations(
            word = word2,
            translations = listOf(translation4, translation5)
        )
        val wordWithTranslations3 = WordWithTranslations(
            word = word3,
            translations = listOf(translation6, translation7, translation8)
        )
        val wordsWithTranslations = listOf(wordWithTranslations1, wordWithTranslations2, wordWithTranslations3)
        val language1 = Language(id = 4427, name = "English")
        val language2 = Language(id = 4426, name = "Russian")
        val languageAndOrder1 = LanguageAndOrder(
            language = language1,
            languageOrder = LanguageOrder(language1.id, language1.id)
        )
        val languageAndOrder2 = LanguageAndOrder(
            language = language2,
            languageOrder = LanguageOrder(language2.id, language2.id)
        )
        val languages = listOf(language1, language2)
        val languageAndOrderList = listOf(languageAndOrder1, languageAndOrder2)
        val source1 = Source(id = 1845, name = "source1", mainOrigLangId = 4427, mainTranslationLangId = 4426)
        val source2 = Source(id = 9734, name = "source2", mainOrigLangId = 4426, mainTranslationLangId = 4427)
        val sourceAndOrder1 = SourceAndOrder(
            source = source1,
            sourceOrder = SourceOrder(source1.id, source1.id)
        )
        val sourceAndOrder2 = SourceAndOrder(
            source = source2,
            sourceOrder = SourceOrder(source2.id, source2.id)
        )
        val sources = listOf(source1, source2)
        val sourceAndOrderList = listOf(sourceAndOrder1, sourceAndOrder2)
        val sourceWithWords1 = SourceWithWords(source = source1, words = listOf(word1, word2, word3))
        val sourceWithWords2 = SourceWithWords(source = source2, words = listOf(word4, word5))
        val sourcesWithWords = listOf(sourceWithWords1, sourceWithWords2)
        val session1 = Session(id = 6119, name = "session1", isActive = false)
        val session2 = Session(id = 9630, name = "session2", isActive = false)
        val sessions = listOf(session1, session2)
    }
}
