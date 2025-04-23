package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorSupportingText(errorText: String) {
    Text(modifier = Modifier.fillMaxWidth(),
         text = errorText,
         color = MaterialTheme.colorScheme.error
    )
}
