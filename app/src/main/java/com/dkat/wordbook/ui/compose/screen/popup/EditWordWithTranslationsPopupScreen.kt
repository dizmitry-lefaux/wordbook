package com.dkat.wordbook.ui.compose.screen.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.CloseablePopupTitle
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.word.EditTranslation
import com.dkat.wordbook.viewModel.screen.EditWordState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "EditWord"

@Composable
fun EditWordWithTranslationsPopupScreen(
    navController: NavController,
    editWordState: EditWordState,
    editWordWithTranslations: (word: Word_B, translations: List<Translation>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var origInput by remember { mutableStateOf(editWordState.currentWord.value) }
    var word by remember { mutableStateOf(editWordState.currentWord) }
    var translation by remember { mutableStateOf(Translation()) }
    val translations = remember { mutableStateListOf<Translation>() }
    // mutableStateMapOf consumes vararg of pairs
    // toTypedArray() -> convert list of Pairs to array of Pairs
    // * -> convert array to vararg of Pairs
    val translationInputs = remember {
        mutableStateMapOf(*(
                editWordState.currentTranslations.mapIndexed { index, translationLocal ->
                    Pair(index, translationLocal.value)
                }.toTypedArray())
        )
    }

    var lastInputFieldId by remember { mutableIntStateOf(editWordState.currentTranslations.size) }

    var isOriginalInputError by remember { mutableStateOf(false) }
    var originalInputErrorText by remember { mutableStateOf("") }

    // isExpanded: workaround to recompose input fields after removal
    var isExpanded by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // needed to have one translation input even if there were attempts to remove it
    if (translationInputs.isEmpty()) {
        translationInputs[lastInputFieldId] = ""
    }

    Popup(properties = PopupProperties(focusable = true)) {
        Column(modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            CloseablePopupTitle(navController = navController,
                                titleText = "Edit word",
            )
            Column {
                Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                    // TODO: move to string resources
                     text = "original word:"
                )
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
                     text = "translations:"
                )
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
                // TODO: extract popup buttons
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
                                       id = editWordState.currentWord.id,
                                       sourceId = editWordState.currentSource.id,
                                       languageId = editWordState.currentSource.mainOrigLangId,
                                       value = origInput
                                   )
                                   translation = Translation(
                                       wordId = editWordState.currentWord.id,
                                       value = localTranslationInput,
                                       languageId = editWordState.currentSource.mainTranslationLangId
                                   )
                                   translations.add(translation)
                               }
                               if (!isOriginalInputError) {
                                   editWordWithTranslations(word, translations.toList())
                                   origInput = ""
                                   navController.popBackStack()
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
                        onClick = { navController.popBackStack() }
                    ) {
                        // TODO: move to string resources
                        ButtonText(buttonText = "Cancel")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditWordWithTranslationsDialogScreenPreview() {
    EditWordWithTranslationsPopupScreen(
        navController = rememberNavController(),
        editWordState = EditWordState(
            currentSource = PreviewData.source1,
            currentWord = PreviewData.word1,
            currentTranslations = listOf(PreviewData.translation1, PreviewData.translation2)
        ),
        editWordWithTranslations = { _, _ -> },
    )
}
