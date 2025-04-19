package com.dkat.wordbook.ui.compose.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B

private const val TAG = "InputWord"

@Composable
fun InputWord(
    sources: List<Source>,
    createWordWithTranslation: (word: Word_B, translation: Translation) -> Unit,
    modifier: Modifier = Modifier,
) {
    var origInput by remember { mutableStateOf("") }
    var translationInput by remember { mutableStateOf("") }
    var source by remember { mutableStateOf(Source()) }
    var word by remember { mutableStateOf(Word_B()) }
    var translation by remember { mutableStateOf(Translation()) }
    var sourceSelected by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = origInput,
            onValueChange = {
                origInput = it
            },
            placeholder = {
                Text("original")
            },
            modifier = modifier.padding(8.dp)
        )
        TextField(
            value = translationInput,
            onValueChange = {
                translationInput = it
            },
            placeholder = {
                Text("translation")
            },
            modifier = modifier.padding(8.dp)
        )
        SourcesDropdown(
            sources = sources,
            label = "select source",
            onSelect = {
                source = it.first
                sourceSelected = it.second
            }
        )
        Button(
            modifier = modifier.padding(8.dp),
            onClick = {
                if (sourceSelected) {
                    word = Word_B(
                        sourceId = source.id, languageId = source.mainOrigLangId, value = origInput
                    )
                    translation = Translation(
                        value = translationInput, languageId = source.mainTranslationLangId
                    )
                    createWordWithTranslation(word, translation)
                    origInput = ""
                    translationInput = ""
                } else {
                    Log.e(TAG, "Source is not selected")
                    // TODO: show error warning
                }
            }
        ) {
            Text(
                text = "Submit values",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

private const val TAG_2 = "SourcesDropdown"

@Composable
fun SourcesDropdown(
    sources: List<Source>,
    label: String,
    onSelect: (Pair<Source, Boolean>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.ArrowDropDown
    } else {
        Icons.Filled.KeyboardArrowUp
    }

    Box(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFieldSize = layoutCoordinates.size.toSize()
                },
            label = { Text(label) },
            trailingIcon = {
                Icon(icon,
                    "contentDescription",
                    Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) {textFieldSize.width.toDp()} )
        ) {
            sources.forEach { source ->
                DropdownMenuItem(
                    onClick = {
                        Log.i(TAG_2, "Source selected: $source")
                        selectedText = source.name
                        onSelect(Pair(source, true))
                    },
                    text = { source.name },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputWordPreview() {
    InputWord(
        sources = listOf(
            Source(id = 3368, name = "Chuck Leonard", mainOrigLangId = 6743, mainTranslationLangId = 9292),
            Source(id = 5360, name = "Wendell Patrick", mainOrigLangId = 7709, mainTranslationLangId = 6304)
        ),
        createWordWithTranslation = {_, _ ->}
    )
}
