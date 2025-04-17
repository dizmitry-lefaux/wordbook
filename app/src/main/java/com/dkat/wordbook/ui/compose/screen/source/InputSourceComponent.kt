package com.dkat.wordbook.ui.compose.screen.source

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source

@Composable
fun InputSource(
    sources: List<Source>,
    languages: List<Language>,
    createSource: (source: Source) -> Unit,
    modifier: Modifier = Modifier,
) {
    var nameInput by remember { mutableStateOf("") }
    var origLangInput by remember { mutableStateOf("")}
    var translationLangInput by remember { mutableStateOf("")}
    var origLangId by remember { mutableStateOf(0)}
    var translationLangId by remember { mutableIntStateOf(0) }
    var source by remember { mutableStateOf(Source()) }

    Column {
        TextField(
            value = nameInput,
            onValueChange = {
                nameInput = it
            },
            placeholder = {
                Text("input source name")
            },
            modifier = modifier.padding(8.dp)
        )
        Row {
            LanguagesDropdown(languages = languages, label = "source original language")
        }
        Row {
            LanguagesDropdown(languages = languages, label = "source translation language")
        }
        Button(
            modifier = modifier.padding(8.dp),
            onClick = {
                if (!sources.map { it.name }.toList().contains(nameInput)) {
                    source = Source(
                        name = nameInput,
                        mainOrigLangId = origLangId,
                        mainTranslationLangId = translationLangId
                    )
                    origLangInput = ""
                    translationLangInput = ""
                    nameInput = ""
                    origLangId = 0
                    translationLangId = 0
                    createSource(source)
                } else {
                    // TODO: show error
                }
            }
        ) {
            Text(
                text = "Add source",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LanguagesDropdown(languages: List<Language>, label: String) {
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
            onValueChange = { selectedText = it },
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
            languages.forEach { language ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = language.name
                    },
                    text = { language.name },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageDropdownPreview() {
    LanguagesDropdown(languages = emptyList(), "label")
}

@Preview(showBackground = true)
@Composable
fun InputSourcePreview() {
    InputSource(
        sources = emptyList(),
        languages = listOf(
            Language(id = 7369, name = "English"),
            Language(id = 7369, name = "Russian"),
        ),
        createSource = {},
    )
}
