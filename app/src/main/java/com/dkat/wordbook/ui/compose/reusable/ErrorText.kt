package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorText(errorText: String) {
    Text(modifier = Modifier.fillMaxWidth().offset(16.dp),
         text = errorText,
         color = MaterialTheme.colorScheme.error,
         style = MaterialTheme.typography.labelSmall
    )
}
