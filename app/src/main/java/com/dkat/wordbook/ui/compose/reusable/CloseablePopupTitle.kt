package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CloseablePopupTitle(
    navController: NavController,
    titleText: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column {
            TitleText(titleText)
        }
        Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    tint = Color.Black,
                    contentDescription = "Close '$titleText' popup"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CloseablePopupTitlePreview() {
    CloseablePopupTitle(navController = rememberNavController(),
                        titleText = "Edit word",
                        modifier = Modifier
    )
}
