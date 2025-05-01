package com.dkat.wordbook.ui.compose.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

private const val TAG = "InputWordWithoutSource"

@Composable
fun InputWordWithoutSource(
    source: Source?,
    createWordWithTranslations: (word: Word_B, translations: List<Translation>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var origInput by remember { mutableStateOf("") }
    var word by remember { mutableStateOf(Word_B()) }
    var translation by remember { mutableStateOf(Translation()) }
    val translations = mutableListOf<Translation>()
    var translationInputs = remember { mutableStateMapOf<Int, String>() }
    var lastInputFieldId by remember { mutableIntStateOf(0) }

    var isOriginalInputError by remember { mutableStateOf(false) }
    var originalInputErrorText by remember { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(true) }
    var alpha by remember { mutableFloatStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()

    if (translationInputs.isEmpty()) {
        translationInputs[lastInputFieldId] = ""
    }

    if (source != null) {
        Column {
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

            Column(modifier = Modifier.alpha(alpha)) {
                // workaround to recompose input fields after removal
                if (isExpanded) {
                    translationInputs.entries.forEach { entry ->
                        Row {
                            TranslationInput(
                                id = entry.key,
                                value = entry.value,
                                getIdOnRemoveClick = {
                                    // workaround to recompose input fields after removal
                                    coroutineScope.launch {
                                        alpha = 0.1f
                                        delay(200L)
                                        translationInputs.remove(entry.key)
                                        isExpanded = false
                                        delay(20L)
                                        isExpanded = true
                                        delay(200L)
                                        alpha = 1f
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
                             contentDescription = "Add translation field"
                        )
                    }
                    Button(modifier = modifier.padding(8.dp),
                           onClick = {
                               Log.i(TAG, "On submit word with translations:" +
                                       "origInput value: $origInput;\n" +
                                       "translation input ids: ${
                                           translationInputs.keys.joinToString(separator = ", ")
                                       };\n" +
                                       "translation input values: ${
                                           translationInputs.values.joinToString(separator = ", ")
                                       }"
                               )

                               if (origInput.isEmpty()) {
                                   isOriginalInputError = true
                                   // TODO: move to string resources
                                   originalInputErrorText = "original is empty"
                               }
                               translationInputs.forEach { translationInputEntity ->
                                   val localTranslationInput = translationInputEntity.value
                                   word = Word_B(
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
                               createWordWithTranslations(word, translations.toList())
                               translations.clear()
                               translationInputs.clear()
                               origInput = ""
                               isOriginalInputError = false
                           }
                    ) {
                        // TODO: move to string resources
                        ButtonText(buttonText = "Submit values")
                    }
                }
            }
        }
    }
}

@Composable
fun TranslationInput(
    id: Int,
    value: String,
    getIdOnRemoveClick: (id: Int) -> Unit,
    getValueOnChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var translationInput by remember { mutableStateOf(value) }
    var isTranslationInputError by remember { mutableStateOf(false) }
    var translationInputErrorText by remember { mutableStateOf("") }

    Row {
        TextField(
            value = translationInput,
            onValueChange = {
                Log.i(TAG, "change value to '$it' for input with id '$id'")
                translationInput = it
                isTranslationInputError = false
                getValueOnChange(it)
            },
            placeholder = {
                // TODO: move to string resources
                Text("input translation")
            },
            modifier = modifier.padding(8.dp),
            isError = isTranslationInputError,
            supportingText = {
                if (isTranslationInputError) {
                    ErrorSupportingText(errorText = translationInputErrorText)
                }
            },
        )
        IconButton(
            onClick = {
                Log.i(TAG, "remove input with id '$id'")
                getIdOnRemoveClick(id)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                tint = Color.Black,
                // TODO: move to string resources
                contentDescription = "Remove translation field"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputWordWithoutSourcePreview() {
    InputWordWithoutSource(
        source = PreviewData.source1,
        createWordWithTranslations = { _, _ -> }
    )
}
