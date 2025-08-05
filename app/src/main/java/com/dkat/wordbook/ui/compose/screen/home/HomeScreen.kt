package com.dkat.wordbook.ui.compose.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.ui.compose.reusable.TitleText
import com.dkat.wordbook.ui.theme.AppTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, ) {
    Column(modifier = modifier.fillMaxSize(),
           verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            // TODO: move to string resources
            TitleText(text = "TODO: Home Screen functionality")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}
