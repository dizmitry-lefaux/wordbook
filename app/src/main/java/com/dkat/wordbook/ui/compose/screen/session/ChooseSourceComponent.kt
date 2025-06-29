package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ErrorText

private const val TAG = "ChooseSourceComponent"

@Composable
fun ChooseSourceComponent(
    currentSource: Source,
    sources: List<Source>,
    getIdOnRemoveClick: (id: Int) -> Unit,
    getValueOnChange: (source: Source) -> Unit,
    modifier: Modifier = Modifier,
) {
    var sourceId by remember { mutableStateOf<Int?>(currentSource.id) }
    var isSourceError by remember { mutableStateOf(false) }
    var sourceErrorText by remember { mutableStateOf("") }

    Row(modifier = modifier.fillMaxWidth()) {
        Column {
            EntityDropdownMenu(list = sources,
                               defaultValue = currentSource.name,
                               onSelect = {
                                   sourceId = it.id
                                   val source = Source(id = it.id,
                                                       name = it.name,
                                                       mainOrigLangId = 0,
                                                       mainTranslationLangId = 0
                                   )
                                   getValueOnChange(source)
                               },
                               resetErrorStateOnClick = { isSourceError = it },
                               isRemovable = true
            )
        }
        if (isSourceError) {
            ErrorText(errorText = sourceErrorText)
        }
        Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
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
}

@Preview(showBackground = true)
@Composable
fun EditSourcePreview() {
    ChooseSourceComponent(sources = PreviewData.sources,
                          getIdOnRemoveClick = { },
                          getValueOnChange = { },
                          currentSource = PreviewData.source1
    )
}
