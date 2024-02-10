package com.strayalphaca.presentation.screens.diary.write.component.block

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.screens.diary.component.ContentIconImage
import com.strayalphaca.presentation.ui.theme.Gray2

@Composable
fun LocationView(
    cityName : String?,
    onClickGpsIcon : () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.location),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 10.dp)
        )

        Text(
            text = cityName ?: stringResource(id = R.string.placeholder_location),
            style = MaterialTheme.typography.body2,
            color = if (cityName != null) MaterialTheme.colors.onSurface else Gray2,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .padding(end = 10.dp)
        )

        ContentIconImage(
            iconId = R.drawable.ic_gps,
            descriptionText = stringResource(id = R.string.select_location),
            onClick = onClickGpsIcon
        )
    }
}