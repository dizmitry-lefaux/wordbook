package com.dkat.wordbook.ui.compose.word

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.Word

@Composable
fun WordItem(
    word: Word,
    onDeleteWordItemClick: (word: Word) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier.padding(4.dp)
        ) {
            Text(
                text = "rus: ${word.rusValue}",
                modifier.padding(2.dp)
            )
            Text(
                text = "eng: ${word.engValue}",
                modifier.padding(2.dp)
            )
        }
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick = { onDeleteWordItemClick(word) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            ) {
                Image(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "",
                    modifier
                        .background(color = Color.LightGray)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordItemPreview()
{
    val word = Word(
        engValue = "engValue1",
        rusValue = "rusValue1",
        sourceName = "source1"
    )
    WordItem(
        word = word,
        onDeleteWordItemClick = {},
        modifier = Modifier
    )
}
