package com.strayalphaca.presentation.components.block

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.Tape

@Composable
fun DiaryInMap(modifier: Modifier, onClick : (Int) -> Unit, thumbnailUrl : String?, id : Int) {
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
                .padding(2.dp)
        ) {
            if (thumbnailUrl == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.8f)
                        .background(Tape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.8f)
                        .background(Tape)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}