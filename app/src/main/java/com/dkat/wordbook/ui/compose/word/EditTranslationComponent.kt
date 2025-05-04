package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText

@Composable
fun EditTranslation(
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
fun EditTranslationPreview() {
    EditTranslation(id = 123,
                    value = "translation value",
                    getIdOnRemoveClick = {},
                    getValueOnChange = {},
    )
}
