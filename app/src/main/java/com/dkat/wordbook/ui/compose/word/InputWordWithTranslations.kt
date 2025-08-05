package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    var currentTranslationInput by remember { mutableStateOf("")}
    var lastTranslationInputFieldId by remember { mutableIntStateOf(0) }
    var translationInputs = remember {
        mutableStateListOf<IndexedTranslationInput>(
            IndexedTranslationInput(index = 0, value = "")
        )
    }

    var isOriginalInputError by remember { mutableStateOf(false) }
    var originalInputErrorText by remember { mutableStateOf("") }

    var isSourceSelected by remember { mutableStateOf(false) }
    isSourceSelected = source.id != 0
    var isShowSourceSelectedError by remember { mutableStateOf(false) }

    LazyColumn {
        item {
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
                HorizontalDivider(thickness = 2.dp)
                // TODO: move to string resources
                Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                     text = "translations:"
                )
            }
        }
        items(items = translationInputs,
              key = { translationInput -> translationInput.index }
        ) {translationInput ->
            InputTranslation(
                id = translationInput.index,
                value = translationInput.value,
                getIdOnRemoveClick = {
                    translationInputs.removeIf { it.index == translationInput.index }
                },
                getValueOnChange = { currentValue ->
                    currentTranslationInput = currentValue
                    translationInputs.replaceAll { replaceableInput ->
                        if (translationInput.index == replaceableInput.index) {
                            IndexedTranslationInput(translationInput.index, currentValue)
                        } else replaceableInput
                    }
                }
            )
        }
        item {
            IconButton(onClick = {
                lastTranslationInputFieldId += 1
                translationInputs.add(IndexedTranslationInput(
                    index = lastTranslationInputFieldId,
                    value = ""
                ))
            }) {
                Icon(imageVector = Icons.Filled.Add,
                     tint = Color.Black,
                    // TODO: move to string resources
                     contentDescription = "Add translation field"
                )
            }
        }
        item {
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
                       translationInputs.forEach { translationInput ->
                           word = Word(
                               sourceId = source.id,
                               languageId = source.mainOrigLangId,
                               value = origInput
                           )
                           translation = Translation(
                               value = translationInput.value,
                               languageId = source.mainTranslationLangId
                           )
                           translations.add(translation)
                       }
                       if (!isOriginalInputError && isSourceSelected) {
                           createWordWithTranslations(word, translations.toList())
                           origInput = ""
                           translationInputs.clear()
                           currentTranslationInput = ""
                           lastTranslationInputFieldId += 1
                           translationInputs.add(IndexedTranslationInput(
                               index = lastTranslationInputFieldId,
                               value = currentTranslationInput
                           ))
                           translations.clear()
                           isOriginalInputError = false
                           isShowSourceSelectedError = false
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

private class IndexedTranslationInput(
    var index: Int,
    var value: String
)

@Preview(showBackground = true)
@Composable
fun InputWordWithTranslationsPreview() {
    InputWordWithTranslations(source = PreviewData.source1,
                                  createWordWithTranslations = { _, _ -> }
    )
}
