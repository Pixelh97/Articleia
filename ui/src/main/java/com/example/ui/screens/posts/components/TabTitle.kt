package com.example.ui.screens.posts.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppTheme

@Composable
fun TabTitle(
    title: String,
    position: Int,
    isSelected: Boolean,
    onClick: (Int) -> Unit,
) {
    val textColor =
        if (isSelected) {
            AppTheme.color.primaryB
        } else {
            AppTheme.color.primaryA
        }
    Text(
        text = title,
        style = AppTheme.textStyle.captionTwo.bold,
        color = textColor,
        modifier =
            Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { onClick(position) },
    )
}
