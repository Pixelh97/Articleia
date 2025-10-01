package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppTheme
import com.example.ui.R

@Composable
fun NoNetworkPlaceholder(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_no_internet),
            contentDescription = stringResource(R.string.no_network_icon),
            tint = AppTheme.color.contentA,
        )

        Text(
            text = stringResource(R.string.oops_no_internet_connection),
            style = AppTheme.textStyle.body.bold,
            color = AppTheme.color.primaryA,
            modifier =
                Modifier
                    .background(Color.White)
                    .padding(top = 16.dp),
        )

        Text(
            text = stringResource(R.string.please_check_your_internet_connection_and_try_again),
            style = AppTheme.textStyle.body.medium,
            color = AppTheme.color.contentB,
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .background(Color.White)
                    .padding(top = 8.dp)
                    .padding(horizontal = 32.dp),
        )

        Button(
            onClick = onRetryClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.color.contentD,
            ),
            modifier = Modifier
                .width(196.dp)
                .padding(top = 32.dp),
            ) {
            Text(
                text = stringResource(R.string.retry),
                style = AppTheme.textStyle.body.medium,
                color = AppTheme.color.primaryA,
            )
        }
    }
}

@Preview(name = "NoNetworkPlaceholder")
@Composable
private fun PreviewNoNetworkPlaceholder() {
    Box(Modifier.background(Color.White)) { NoNetworkPlaceholder({}) }
}
