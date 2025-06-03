package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ErrorText

@Composable
fun ChooseSourceComponent(
    sources: List<Source>,
    getIdOnRemoveClick: (id: Int) -> Unit,
    getValueOnChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var sourceId by remember { mutableStateOf<Int?>(null) }
    var isSourceError by remember { mutableStateOf(false) }
    var sourceErrorText by remember { mutableStateOf("") }

    Row {
        EntityDropdownMenu(list = sources,
                           defaultValue = "select source",
                           onSelect = { sourceId = it.id },
                           resetErrorStateOnClick = { isSourceError = it })
        if (isSourceError) {
            ErrorText(errorText = sourceErrorText)
        }
        IconButton(
            onClick = {
                getIdOnRemoveClick(sourceId!!)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                tint = Color.Black,
                // TODO: move to string resources
                contentDescription = "Remove source field"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditSourcePreview() {
    ChooseSourceComponent(sources = PreviewData.sources,
                          getIdOnRemoveClick = { },
                          getValueOnChange = { },
    )
}
