package com.dkat.wordbook.ui.compose.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.Word

@Composable
fun CardWithAnimatedAlpha(
    word: Word,
    modifier: Modifier = Modifier
)
{
    var visible by remember {
        mutableStateOf(false)
    }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0f,
        label = "alpha"
    )
    Column (
        modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                visible = !visible
            }
    ) {
        Text(
            text = "Rus value: ${word.rusValue}".trimMargin(),
        )
        Box() {
            Row {
                Text(
                    text = "Eng value: ",
                )
                Text(
                    text = word.engValue,
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .graphicsLayer {
                            alpha = animatedAlpha
                        }
                )
            }
        }
        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
fun CardWithAnimatedAlphaPreview()
{
    CardWithAnimatedAlpha(
        Word(
            id = 3507,
            rusValue = "penatibus",
            engValue = "facilisi",
            sourceName = "Harley Spencer"
        )
    )
}
