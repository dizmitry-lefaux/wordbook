package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ButtonText(buttonText: String) {
    Text(text = buttonText,
         style = MaterialTheme.typography.titleLarge,
         fontWeight = FontWeight.Bold,
         textAlign = TextAlign.Center
    )
}
