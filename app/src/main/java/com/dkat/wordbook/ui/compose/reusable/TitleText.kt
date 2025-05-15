package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TitleText(text: String,
              modifier: Modifier = Modifier
) {
    Text(text = text,
         modifier = modifier.padding(12.dp),
         style = MaterialTheme.typography.titleLarge,
         fontWeight = FontWeight.Bold,
         textAlign = TextAlign.Left
    )
}
