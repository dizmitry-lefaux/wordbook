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
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.reusable.ErrorText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "InputWordWithTranslations"

@Composable
fun InputWordWithTranslations(
    source: Source,
    createWordWithTranslations: (word: Word, translations: List<Translation>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var origInput by remember { mutableStateOf("") }
    var word by remember { mutableStateOf(Word()) }
    var translation by remember { mutableStateOf(Translation()) }
    val translations = mutableListOf<Translation>()
    val translationInputs = remember { mutableStateMapOf<Int, String>() }
    var lastInputFieldId by remember { mutableIntStateOf(0) }

    var isOriginalInputError by remember { mutableStateOf(false) }
    var originalInputErrorText by remember { mutableStateOf("") }

    var isSourceSelected by remember { mutableStateOf(false) }
    isSourceSelected = source.id != 0
    var isShowSourceSelectedError by remember { mutableStateOf(false) }

    // isExpanded: workaround to recompose input fields on removal and submit
    var isExpanded by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // needed to have one translation input even if there were attempts to remove it
    if (translationInputs.isEmpty()) {
        translationInputs[lastInputFieldId] = ""
    }

    Column {
        Column {
            Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                 text = "original:"
            )
            TextField(
                value = origInput,
                onValueChange = {
                    origInput = it
                    isOriginalInputError = false
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
                 text = "translations:"
            )

            if (isExpanded) {
                translationInputs.entries.forEach { entry ->
                    Row {
                        InputTranslation(
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
            }
            if (!isExpanded) {
                translationInputs.entries.forEach { entry ->
                    Row {
                        InputTranslation(id = entry.key,
                                         value = entry.value,
                                         getIdOnRemoveClick = {},
                                         getValueOnChange = {}
                        )
                    }
                }
            }

            Column {
                IconButton(onClick = {
                    lastInputFieldId += 1
                    translationInputs[lastInputFieldId] = ""
                }
                ) {
                    Icon(imageVector = Icons.Filled.Add,
                         tint = Color.Black,
                        // TODO: move to string resources
                         contentDescription = "Add translation field"
                    )
                }
                // TODO: possibly move out of conditional 'if(isExpanded)'
                Button(modifier = modifier.padding(8.dp),
                       onClick = {
                           if (!isSourceSelected) {
                               isShowSourceSelectedError = true
                           }
                           if (origInput.isEmpty()) {
                               isOriginalInputError = true
                               // TODO: move to string resources
                               originalInputErrorText = "original is empty"
                           }
                           translationInputs.forEach { translationInputEntity ->
                               val localTranslationInput = translationInputEntity.value
                               word = Word(
                                   sourceId = source.id,
                                   languageId = source.mainOrigLangId,
                                   value = origInput
                               )
                               translation = Translation(
                                   value = localTranslationInput,
                                   languageId = source.mainTranslationLangId
                               )
                               translations.add(translation)
                           }
                           if (!isOriginalInputError && isSourceSelected) {
                               createWordWithTranslations(word, translations.toList())
                               origInput = ""
                               translationInputs.clear()
                               translations.clear()
                               isOriginalInputError = false
                               isShowSourceSelectedError = false
                               coroutineScope.launch {
                                   isExpanded = false
                                   delay(100L)
                                   isExpanded = true
                               }
                           } else {
                               // TODO
                           }
                       }
                ) {
                    // TODO: move to string resources
                    ButtonText(buttonText = "Submit")
                }
                if (!isSourceSelected && isShowSourceSelectedError) {
                    ErrorText(errorText = "source not selected",
                              modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputWordWithTranslationsPreview() {
    InputWordWithTranslations(source = PreviewData.source1,
                              createWordWithTranslations = { _, _ -> }
    )
}
