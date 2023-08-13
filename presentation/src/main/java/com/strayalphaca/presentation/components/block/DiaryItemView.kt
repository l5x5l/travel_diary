package com.strayalphaca.presentation.components.block

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strayalphaca.presentation.ui.theme.Tape

@Composable
fun DiaryItemView(
    modifier : Modifier = Modifier,
    onClick : (String) -> Unit,
    imageUrl : String? = null,
    leftText : String,
    rightText : String? = null,
    id : String
) {
    Surface(
        modifier = modifier
            .padding(2.dp)
            .shadow(4.dp)
            .clickable { onClick(id) },
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f)
                    .background(Tape)
            ) {
                imageUrl?.let{ imageUrl ->
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = imageUrl,
                        contentDescription = "image"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(leftText, style = MaterialTheme.typography.body2)
                rightText?.let {
                    Text(text = it, style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}