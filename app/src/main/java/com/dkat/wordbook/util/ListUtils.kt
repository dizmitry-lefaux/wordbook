package com.dkat.wordbook.util

class ListUtils {
    companion object {
        fun <Item> getSwappedElements(list1: List<Item>, list2: List<Item>): List<Item> {
            if (list1.size != list2.size) {
                return emptyList()
            }
            if (!list1.containsAll(list2)) {
                return emptyList()
            }
            var result = mutableListOf<Item>()
            list1.forEachIndexed { index, item ->
                if (list1[index] != list2[index]) {
                    result.add(list1[index])
                }
            }
            return result
        }
    }
}
