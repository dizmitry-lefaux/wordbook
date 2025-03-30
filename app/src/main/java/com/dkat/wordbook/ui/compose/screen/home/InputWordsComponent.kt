package com.dkat.wordbook.ui.compose.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.Word

@Composable
fun InputWord(
    addWord:(word: Word) -> Unit,
    modifier: Modifier = Modifier,
)
{
    var rusInput by remember { mutableStateOf("") }
    var engInput by remember { mutableStateOf("") }
    var sourceInput by remember { mutableStateOf("") }
    var word by remember { mutableStateOf(Word()) }

    Column {
        TextField(
            value = rusInput,
            onValueChange = {
                rusInput = it
            },
            placeholder = {
                Text("input rus word here")
            },
            modifier = modifier.padding(8.dp)
        )
        TextField(
            value = engInput,
            onValueChange = {
                engInput = it
            },
            placeholder = {
                Text("input eng word here")
            },
            modifier = modifier.padding(8.dp)
        )
        TextField(
            value = sourceInput,
            onValueChange = {
                sourceInput = it
            },
            placeholder = {
                Text("input source name here")
            },
            modifier = modifier.padding(8.dp)
        )
        Button(
            modifier = modifier.padding(8.dp),
            onClick = {
                word = Word(
                    rusValue = rusInput,
                    engValue = engInput,
                    sourceName = sourceInput
                )
                rusInput = ""
                engInput = ""
                sourceInput = sourceInput
                addWord(word)
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

@Preview(showBackground = true)
@Composable
fun InputWordPreview() {
    InputWord(
        addWord = {}
    )
}
