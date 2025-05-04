package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "EditWord"

@Composable
fun EditWordWithTranslations(
    source: Source,
    inputWord: Word_B,
    inputTranslations: List<Translation>,
    editWordWithTranslations: (word: Word_B, translations: List<Translation>) -> Unit,
    exitEditMode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var origInput by remember { mutableStateOf(inputWord.value) }
    var word by remember { mutableStateOf(inputWord) }
    var translation by remember { mutableStateOf(Translation()) }
    val translations = remember { mutableStateListOf<Translation>() }
    // mutableStateMapOf consumes vararg of pairs
    // toTypedArray() -> convert list of Pairs to array of Pairs
    // * -> convert array to vararg of Pairs
    val translationInputs = remember {
        mutableStateMapOf(*(
                inputTranslations.mapIndexed { index, translationLocal ->
                    Pair(index, translationLocal.value)
                }.toTypedArray())
        )
    }

    var lastInputFieldId by remember { mutableIntStateOf(inputTranslations.size) }

    var isOriginalInputError by remember { mutableStateOf(false) }
    var originalInputErrorText by remember { mutableStateOf("") }

    // isExpanded: workaround to recompose input fields after removal
    var isExpanded by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // needed to have one translation input even if there were attempts to remove it
    if (translationInputs.isEmpty()) {
        translationInputs[lastInputFieldId] = ""
    }

    Column {
        Column {
            Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                // TODO: move to string resources
                 text = "original word:")
            TextField(
                value = origInput,
                onValueChange = {
                    origInput = it
                    isOriginalInputError = false
                    if (it.isEmpty()) {
                        // TODO: move to string resources
                        origInput = "input original word"
                    }
                },
                placeholder = {
                    // TODO: move to string resources
                    Text("input original")
                },
                modifier = modifier.padding(8.dp),
                isError = isOriginalInputError,
                supportingText = {
                    if (isOriginalInputError) {
                        ErrorSupportingText(errorText = originalInputErrorText)
                    }
                },
            )
        }

        Column {
            HorizontalDivider(thickness = 2.dp)
            // TODO: move to string resources
            Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                 text = "translations:")
            if (isExpanded) {
                translationInputs.entries.forEach { entry ->
                    Row {
                        EditTranslation(
                            id = entry.key,
                            value = entry.value,
                            getIdOnRemoveClick = {
                                coroutineScope.launch {
                                    isExpanded = false
                                    translationInputs.remove(entry.key)
                                    delay(100L)
                                    isExpanded = true
                                }
                            },
                            getValueOnChange = {
                                translationInputs.replace(entry.key, it)
                            }
                        )
                    }
                }
                IconButton(onClick = {
                    lastInputFieldId += 1
                    translationInputs[lastInputFieldId] = ""
                }) {
                    Icon(imageVector = Icons.Filled.Add,
                         tint = Color.Black,
                        // TODO: move to string resources
                         contentDescription = "Add translation field"
                    )
                }
            }
            if (!isExpanded) {
                translationInputs.entries.forEach { entry ->
                    Row {
                        EditTranslation(id = entry.key,
                                        value = entry.value,
                                        getIdOnRemoveClick = {},
                                        getValueOnChange = {}
                        )
                    }
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Add,
                         tint = Color.Black,
                        // TODO: move to string resources
                         contentDescription = "Add translation field"
                    )
                }
            }
            Row {
                Button(modifier = modifier.padding(8.dp),
                       onClick = {
                           if (origInput.isEmpty()) {
                               isOriginalInputError = true
                               // TODO: move to string resources
                               originalInputErrorText = "original is empty"
                           }
                           translationInputs.forEach { translationInputEntity ->
                               val localTranslationInput = translationInputEntity.value
                               word = Word_B(
                                   id = inputWord.id,
                                   sourceId = source.id,
                                   languageId = source.mainOrigLangId,
                                   value = origInput
                               )
                               translation = Translation(
                                   wordId = inputWord.id,
                                   value = localTranslationInput,
                                   languageId = source.mainTranslationLangId
                               )
                               translations.add(translation)
                           }
                           if (!isOriginalInputError) {
                               editWordWithTranslations(word, translations.toList())
                               exitEditMode()
                               origInput = ""
                           } else {
                               // TODO
                           }
                       }
                ) {
                    // TODO: move to string resources
                    ButtonText(buttonText = "Submit")
                }
                Button(
                    modifier = modifier.padding(8.dp),
                    onClick = { exitEditMode() }
                ) {
                    // TODO: move to string resources
                    ButtonText(buttonText = "Cancel")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditWordPreview() {
    EditWordWithTranslations(
        source = PreviewData.source1,
        inputWord = PreviewData.word1,
        inputTranslations = listOf(PreviewData.translation1, PreviewData.translation2),
        editWordWithTranslations = { _, _ -> },
        exitEditMode = {}
    )
}
