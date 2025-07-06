package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.ui.semantics.CustomAccessibilityAction

fun <Item> getCustomAccessibilityActions (
    index: Int,
    list: List<Item>,
) : List<CustomAccessibilityAction> {
    var mutableList = list
    return listOf(
        CustomAccessibilityAction(
            label = "Move Up",
            action = {
                if (index > 0) {
                    mutableList = list.toMutableList().apply {
                        add(index - 1, removeAt(index))
                    }
                    true
                } else {
                    false
                }
            }
        ),
        CustomAccessibilityAction(
            label = "Move Down",
            action = {
                if (index < list.size - 1) {
                    mutableList = list.toMutableList().apply {
                        add(index + 1, removeAt(index))
                    }
                    true
                } else {
                    false
                }
            }
        ),
    )
}
