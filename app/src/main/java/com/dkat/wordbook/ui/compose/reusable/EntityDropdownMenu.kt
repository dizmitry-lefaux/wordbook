package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.NamedEntity

@Composable
fun EntityDropdownMenu(
    list: List<NamedEntity>,
    defaultValue: String,
    onSelect: (NamedEntity) -> Unit,
    resetErrorStateOnClick: (Boolean) -> Unit,
    isRemovable: Boolean = false
) {
    var currentValue by remember { mutableStateOf(defaultValue) }
    var currentId by remember { mutableIntStateOf(Int.MIN_VALUE) }
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = if (isRemovable) Modifier else Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp).clickable {
                    isExpanded = true
                    resetErrorStateOnClick(false)
                }
            ) {
                Text(text = currentValue)
                Image(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "dropdown icon",
                )
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                list.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item.name)
                        },
                        onClick = {
                            isExpanded = false
                            currentValue = item.name
                            currentId = item.id
                            onSelect(item)
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntityDropdownMenuPreview() {
    EntityDropdownMenu(
        list = listOf(
            Language(id = 2510, name = "Pierre Jones"),
            Language(id = 3926, name = "Harlan Burks")
        ),
        defaultValue = "source original language",
        onSelect = {},
        resetErrorStateOnClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EntityDropdownMenuRemovablePreview() {
    EntityDropdownMenu(
        list = listOf(
            Language(id = 2510, name = "Pierre Jones"),
            Language(id = 3926, name = "Harlan Burks")
        ),
        defaultValue = "source original language",
        onSelect = {},
        resetErrorStateOnClick = {},
        isRemovable = true
    )
}
