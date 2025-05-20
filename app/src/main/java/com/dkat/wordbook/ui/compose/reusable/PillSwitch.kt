package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// TODO: check https://cristian-code.medium.com/jetpack-compose-toggle-button-c6628cf44c05
@Composable
fun PillSwitch(
    modifier: Modifier = Modifier,
    pills: List<PillData>
) {
    Column() {
        Row(modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {}

        Row(modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row {
                pills.forEach { pill ->
                    PillElement(
                        modifier = modifier.weight(1f),
                        onClick = pill.onClick,
                        titleText = pill.title,
                        isSelected = pill.isSelected
                    )
                }
            }
        }
    }
}

data class PillData(
    val title: String,
    var isSelected: Boolean,
    val onClick: () -> Unit,
)

@Composable
fun PillElement(
    modifier: Modifier = Modifier,
    titleText: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.background
        else MaterialTheme.colorScheme.secondaryContainer.copy(0.0f)
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = modifier
            .background(
                color = backgroundColor,
                RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            ).clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }.clip(RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.Center,
    ) {
        val textColor =
            if (isSelected) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.primary
        val textStyle =
            if (isSelected) MaterialTheme.typography.labelLarge
            else MaterialTheme.typography.labelSmall

        Text(text = titleText,
             modifier = modifier.padding(8.dp),
             color = textColor,
             style = textStyle,
             lineHeight = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PillSwitchPreview() {
    PillSwitch(
        pills = listOf(PillData(title = "title1", isSelected = false) {},
                       PillData(title = "title2", isSelected = true) {},
                       PillData(title = "title3", isSelected = false) {}
        )
    )
}
