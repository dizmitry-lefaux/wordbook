package com.dkat.wordbook.ui.compose.card

// TODO: update with new word with translations entities

//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.dkat.wordbook.data.entity.Word
//
//@Composable
//fun CardWithAnimatedSize(word: Word)
//{
//    var expanded by remember { mutableStateOf(false) }
//    Column(
//        Modifier
//            .fillMaxWidth()
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null
//            ) {
//                expanded = !expanded
//            }
//    ) {
//        Text(
//            text = "Rus value: ${word.rusValue}",
//        )
//        Text(
//            text = "Eng value: ${word.engValue}",
//            Modifier
//                .animateContentSize()
//                .height(if (expanded) 100.dp else 0.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CardWithAnimatedSizePreview()
//{
//    CardWithAnimatedSize(
//        Word(
//            id = 3507,
//            rusValue = "penatibus",
//            engValue = "facilisi",
//            sourceName = "Harley Spencer"
//        )
//    )
//}
