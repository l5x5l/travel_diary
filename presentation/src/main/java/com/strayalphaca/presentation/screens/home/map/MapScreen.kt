package com.strayalphaca.presentation.screens.home.map

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.components.atom.gradient_box.GradientBox
import com.strayalphaca.presentation.components.atom.gradient_box.GradientDirection
import com.strayalphaca.presentation.components.block.MapEmptyView
import com.strayalphaca.presentation.components.template.traily_map.TrailyMap
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    goToDiaryList : (Int) -> Unit = {},
    viewModel : MapViewModel
) {

    val state by viewModel.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 16.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .align(Alignment.Center),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                GradientBox(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.TopStart),
                    gradientDirection = GradientDirection.OnBottom
                )

                GradientBox(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.TopEnd),
                    gradientDirection = GradientDirection.OnLeft
                )

                GradientBox(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.BottomEnd),
                    gradientDirection = GradientDirection.OnTop
                )

                GradientBox(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.BottomStart),
                    gradientDirection = GradientDirection.OnRight
                )
            }

            Box(
                Modifier
                    .padding(16.dp)
                    .border(width = 1.dp, color = MaterialTheme.colors.onSurface)
                    .padding(16.dp)
            ) {
                if (state.currentLocationId != null) {
                    BaseIconButton(
                        iconResourceId = R.drawable.ic_back,
                        onClick = {
                            viewModel.loadLocationDiaryList(null)
                        }
                    )
                }

                TrailyMap(
                    modifier = Modifier.fillMaxSize(),
                    locationId = state.currentLocationId,
                    locationDiaryList = state.dataList,
                    onClickDiary = { id ->
                        if (viewModel.isLeafLocation()) {
                            goToDiaryList(id)
                        } else {
                            viewModel.loadLocationDiaryList(id)
                        }
                    }
                )
            }

            if (state.showEmpty) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.surface.copy(alpha = 0.8f))
                ) {
                    MapEmptyView(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .align(Alignment.Center)
                            .zIndex(4f)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
@Composable
fun MapScreenPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // MapScreen()
        }
    }
}