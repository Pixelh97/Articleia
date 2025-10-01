package com.example.ui.screens.posts.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.AppTheme
import com.example.ui.R

@Composable
fun NoDataFoundPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(
            text = stringResource(R.string.could_not_find_any_data_at_the_current_time),
            style = AppTheme.textStyle.title.bold,
            color = AppTheme.color.primaryA,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "NoDataFoundPlaceholder")
@Composable
private fun PreviewNoDataFoundPlaceholder() {
    NoDataFoundPlaceholder()
}