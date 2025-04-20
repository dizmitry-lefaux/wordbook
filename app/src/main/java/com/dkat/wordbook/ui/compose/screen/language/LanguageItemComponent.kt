package com.dkat.wordbook.ui.compose.screen.language

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Language

@Composable
fun LanguageItem(
    language: Language,
    onDeleteLanguageItemClick: (language: Language) -> Unit,
    modifier: Modifier
) {
    Row(modifier.padding(4.dp).fillMaxWidth()) {
        Column(
            modifier.padding(4.dp)
        ) {
            Text(
                modifier = modifier.padding(2.dp),
                text = language.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
        }
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick = { onDeleteLanguageItemClick(language) },
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
    val language = Language(
        id = 6491, name = "English"

    )
    LanguageItem(
        language = language,
        onDeleteLanguageItemClick = {},
        modifier = Modifier
    )
}
