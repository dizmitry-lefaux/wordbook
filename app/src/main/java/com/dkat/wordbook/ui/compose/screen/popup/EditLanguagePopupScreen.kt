package com.dkat.wordbook.ui.compose.screen.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.CloseablePopupTitle
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText

@Composable
fun EditLanguagePopupScreen(
    navController: NavController,
    editLanguageState: Language,
    editLanguage: (language: Language) -> Unit,
    modifier: Modifier = Modifier,
) {
    var languageInput by remember { mutableStateOf(editLanguageState.name) }
    var isLanguageInputError by remember { mutableStateOf(false) }
    var languageInputErrorText by remember { mutableStateOf("") }
    var language by remember { mutableStateOf(Language()) }

    Popup(properties = PopupProperties(focusable = true)) {
        Column(modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            CloseablePopupTitle(navController = navController,
                                titleText = "Edit language",
            )
            Column {
                Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                    // TODO: move to string resources
                     text = "language name:"
                )
                TextField(
                    value = languageInput,
                    onValueChange = {
                        languageInput = it
                        isLanguageInputError = false
                        if (it.isEmpty()) {
                            // TODO: move to string resources
                            languageInputErrorText = "input language name"
                            isLanguageInputError = true
                        }
                    },
                    placeholder = {
                        // TODO: move to string resources
                        Text("input language name")
                    },
                    modifier = modifier.padding(8.dp),
                    isError = isLanguageInputError,
                    supportingText = {
                        if (isLanguageInputError) {
                            ErrorSupportingText(errorText = languageInputErrorText)
                        }
                    },
                )
                Row {
                    Button(modifier = modifier.padding(8.dp),
                           onClick = {
                               if (languageInput.isEmpty()) {
                                   isLanguageInputError = true
                                   // TODO: move to string resources
                                   languageInputErrorText = "language name is empty"
                               }
                               language = Language(id = editLanguageState.id, name = languageInput)
                               if (!isLanguageInputError) {
                                   editLanguage(language)
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
fun PreviewEditLanguagePopupScreen() {
    EditLanguagePopupScreen(
        navController = rememberNavController(),
        editLanguageState = PreviewData.language1,
        editLanguage = { _ -> },
        modifier = Modifier,
    )
}
